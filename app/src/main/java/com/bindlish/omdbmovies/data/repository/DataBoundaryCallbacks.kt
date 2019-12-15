package com.bindlish.omdbmovies.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagedList
import com.bindlish.omdbmovies.data.DataApi
import com.bindlish.omdbmovies.data.DataItem
import com.bindlish.omdbmovies.db.DataDao
import io.reactivex.schedulers.Schedulers

class DataBoundaryCallbacks(
    private val query : String,
    private val dataApi : DataApi,
    private val dataDao : DataDao) : PagedList.BoundaryCallback<DataItem>() {

    private var lastPageNum = 1

    private var _errorString = MutableLiveData<String>()
    val errorString: LiveData<String>
        get() = _errorString

    private var isRequestInProgress = MutableLiveData<Boolean>()
        init {
            isRequestInProgress.postValue(false)
        }
    val inProcess : LiveData<Boolean>
        get() = isRequestInProgress

    override fun onZeroItemsLoaded() {
        requestAndSaveData(query)
    }

    override fun onItemAtEndLoaded(itemAtEnd: DataItem) {
        requestAndSaveData(query)
    }

    private fun requestAndSaveData(query: String) {
        if (isRequestInProgress.value!!) return

        isRequestInProgress.postValue(true)
        dataApi.fetchMovies(query, lastPageNum)
            .subscribeOn(Schedulers.io())
            .subscribe({it ->
                it?.let {
                    dataDao.insertData(it.Search)
                }.also {
                    lastPageNum++
                    isRequestInProgress.postValue(false)
                }
            }, {error ->
                _errorString.postValue(error.message)
                isRequestInProgress.postValue(false)
            })
    }
}