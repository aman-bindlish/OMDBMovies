package com.bindlish.omdbmovies.ui

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import com.bindlish.omdbmovies.R
import com.bindlish.omdbmovies.data.DataItem
import com.bindlish.omdbmovies.data.network.Status

import com.bindlish.omdbmovies.ui.adapter.MoviesListAdapter
import com.bindlish.omdbmovies.utils.observableFromSearch
import com.bindlish.omdbmovies.viewmodel.MoviesViewModel
import com.bindlish.omdbmovies.viewmodel.ViewModelFactory
import dagger.android.AndroidInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_movies_list.*
import kotlinx.android.synthetic.main.error_layout.*
import kotlinx.android.synthetic.main.movies_list.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * An activity representing a list of news.
 */
class MoviesListActivity : AppCompatActivity() {

    private var twoPane: Boolean = false
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: MoviesViewModel
    private lateinit var listAdapter: MoviesListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies_list)
        AndroidInjection.inject(this)

        // casting custom toolbar and set it as action bar
        setSupportActionBar(toolbar)
        // configuring behaviour of toolbar
        supportActionBar?.setDisplayShowCustomEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MoviesViewModel::class.java)

        if (item_detail_container != null) {
            twoPane = true
        }
        initialiseViews()
    }

    private fun initialiseViews() {
        movies_list?.apply {
            listAdapter = MoviesListAdapter(this@MoviesListActivity, twoPane)
            adapter = listAdapter
            setHasFixedSize(true)
        }
        viewModel.apply {
            searchMovies()
            // observe live data from view model
            searchResponse.observe(this@MoviesListActivity, Observer {
                 it?.let {
                     displayData(it)
                 }
            })
            errorString.observe(this@MoviesListActivity, Observer {
                it?.let {
                    displayErrorLayout()
                }
            })
            apiInProcess.observe(this@MoviesListActivity, Observer {
                it?.let {
                    if(it){
                        displayLoadingView()
                    } else {
                        hideShimmer()
                    }
                }
            })
        }
        // handling click for retry, hit api and show shimmer
        retry_button.setOnClickListener {
            onRefresh()
        }
    }

    private fun onRefresh() {
        viewModel.searchMovies()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        when(item.itemId){
            R.id.search -> {
                item.expandActionView()
                val searchView = item.actionView as SearchView
                searchView.setSearchableInfo(
                    searchManager
                        .getSearchableInfo(getComponentName())
                )
                searchView.maxWidth = Integer.MAX_VALUE

                observableFromSearch(searchView)
                    .debounce(300, TimeUnit.MILLISECONDS)
                    .filter { text -> text.isNotEmpty() }
                    .distinctUntilChanged()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        viewModel.searchMovies(it)
                    }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // method to display data into list after fetching from repository
    private fun displayData(data: PagedList<DataItem>?) {
        data?.let {
            listAdapter.submitList(it)
            error_layout.visibility = View.GONE
            hideShimmer()
            movies_list?.scheduleLayoutAnimation()
        }
    }

    // stop shimmer effect in onPause
    override fun onPause() {
        hideShimmer()
        super.onPause()
    }

    // method to show loading view
    private fun displayLoadingView() {
        if (listAdapter.itemCount == 0) {
            showShimmer()
        }
        error_layout.visibility = View.GONE
    }

    // method to show error layout
    private fun displayErrorLayout() {
        error_layout.visibility = View.VISIBLE
        movies_list?.visibility = View.GONE
        hideShimmer()
    }

    // method to show and start shimmer effect
    private fun showShimmer() {
        shimmer_view_container.visibility = View.VISIBLE
        shimmer_view_container.startShimmer()
    }

    // method to hide and stop shimmer affect
    private fun hideShimmer() {
        shimmer_view_container.stopShimmer()
        shimmer_view_container.visibility = View.GONE
    }
}
