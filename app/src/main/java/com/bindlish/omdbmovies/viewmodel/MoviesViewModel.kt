package com.bindlish.omdbmovies.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.bindlish.omdbmovies.data.DataItem
import com.bindlish.omdbmovies.data.ResponseData
import com.bindlish.omdbmovies.data.network.Resource
import com.bindlish.omdbmovies.data.repository.DataRepository
import javax.inject.Inject

class MoviesViewModel @Inject constructor(private val dataRepository: DataRepository) :
    ViewModel() {

    companion object {
        var DEFAULT_SEARCH_KEY = "spider"
    }

    private val movieDataId = MutableLiveData<String>()
    private val moviesLiveData: LiveData<DataItem> = Transformations.switchMap(
        movieDataId,
        { input -> dataRepository.getDataById(input) }
    )

    fun getMoviesData() = moviesLiveData

    fun fetchMoviesById(id : String) {
        movieDataId.value = id
    }

    private val searchLiveData = MutableLiveData<String>()

    private val searchResult: LiveData<ResponseData> = Transformations.map(searchLiveData, {
        dataRepository.getData(it)
    })

    val searchResponse: LiveData<PagedList<DataItem>> = Transformations.switchMap(searchResult,
        { it.data })
    val errorString: LiveData<String> = Transformations.switchMap(searchResult,
        { it.errorString })
    val apiInProcess: LiveData<Boolean> = Transformations.switchMap(searchResult,
        { it.inProcess })

    fun searchMovies(query: String = DEFAULT_SEARCH_KEY) {
        searchLiveData.postValue(query)
    }
}
