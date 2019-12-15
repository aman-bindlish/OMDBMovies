package com.bindlish.omdbmovies.ui.adapter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bindlish.omdbmovies.BR
import com.bindlish.omdbmovies.R
import com.bindlish.omdbmovies.data.DataItem
import com.bindlish.omdbmovies.data.ResponseData
import com.bindlish.omdbmovies.databinding.MoviesListContentBinding
import com.bindlish.omdbmovies.ui.MoviesDetailActivity
import com.bindlish.omdbmovies.ui.MoviesDetailFragment
import com.bindlish.omdbmovies.ui.MoviesListActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


class MoviesListAdapter(
    private val parentActivity: AppCompatActivity,
    private val twoPane: Boolean
) : PagedListAdapter<DataItem, MoviesListAdapter.ViewHolder>(DIFF_UTIL_CALLBACK) {

    private val onClickListener: View.OnClickListener

    companion object {
        private val DIFF_UTIL_CALLBACK = object : DiffUtil.ItemCallback<DataItem>() {
            override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean =
                oldItem == newItem
        }
    }

    init {
        onClickListener = View.OnClickListener { v ->
            val item = v.tag as DataItem
            if (twoPane) {
                val fragment = MoviesDetailFragment().apply {
                    arguments = Bundle().apply {
                        putString(MoviesDetailFragment.ARG_MOVIE_ID, item.id)
                    }
                }
                parentActivity.supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.item_detail_container, fragment)
                    .commit()
            } else {
                val intent = Intent(v.context, MoviesDetailActivity::class.java).apply {
                    putExtra(MoviesDetailFragment.ARG_MOVIE_ID, item.id)
                }
                v.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding : MoviesListContentBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.movies_list_content, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item : DataItem? = getItem(position)
        item?.let {
            holder.bind(item)
            with(holder.itemView) {
                tag = item
                setOnClickListener(onClickListener)
            }
            item.poster?.let {
                val requestOptions = RequestOptions()
                requestOptions.placeholder(R.mipmap.movie_icon)
                requestOptions.error(R.mipmap.movie_icon)
                Glide.with(holder.itemView.context).load(it).apply(requestOptions).into(holder.movieImg)
            }
        }
    }

    inner class ViewHolder(val binding: MoviesListContentBinding) : RecyclerView.ViewHolder(binding.root) {
        val movieImg : ImageView = binding.movieImg

        fun bind(data : DataItem) {
            binding.setVariable(BR.movie, data)
            binding.executePendingBindings()
        }
    }
}