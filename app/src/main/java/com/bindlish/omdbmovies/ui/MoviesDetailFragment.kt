package com.bindlish.omdbmovies.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bindlish.omdbmovies.R
import com.bindlish.omdbmovies.databinding.MoviesDetailBinding
import com.bindlish.omdbmovies.viewmodel.MoviesViewModel
import com.bindlish.omdbmovies.viewmodel.ViewModelFactory
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.activity_movies_detail.*
import javax.inject.Inject

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a [MoviesListActivity]
 * in two-pane mode (on tablets) or a [MoviesDetailActivity]
 * on handsets.
 */
class MoviesDetailFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var binding: MoviesDetailBinding
    private lateinit var viewModel: MoviesViewModel

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.movies_detail, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MoviesViewModel::class.java)
        arguments?.let {
            if (it.containsKey(ARG_MOVIE_ID)) {
                viewModel.fetchMoviesById(it.getString(ARG_MOVIE_ID)!!)
                viewModel.getMoviesData().observe(viewLifecycleOwner, Observer {data ->
                    binding.movie = data
                    data?.poster.let {
                        val requestOptions = RequestOptions()
                        requestOptions.placeholder(R.mipmap.movie_icon)
                        requestOptions.error(R.mipmap.movie_icon)
                        Glide.with(this@MoviesDetailFragment).load(it).apply(requestOptions).into(binding.movieImg)
                    }
                })
            }
        }
    }

    companion object {
        const val ARG_MOVIE_ID = "movie_id"
    }
}
