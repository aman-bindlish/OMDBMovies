package com.bindlish.omdbmovies.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import com.bindlish.omdbmovies.data.DataApi
import com.bindlish.omdbmovies.data.DataItem
import com.bindlish.omdbmovies.data.ResponseData
import com.bindlish.omdbmovies.db.DataDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepository @Inject constructor(
    private val dataDao: DataDao,
    private val dataApi: DataApi,
    private val appRequestExecutors: AppRequestExecutors = AppRequestExecutors()
) {

    companion object {
        private const val PAGE_SIZE = 10
    }

    fun getDataById(id : String) : LiveData<DataItem> = dataDao.getDataById(id)

    fun getData(query: String): ResponseData {
        val dataSourceFactory = dataDao.getDataWithTitle(query)
        val boundaryCallback = DataBoundaryCallbacks(query, dataApi, dataDao)
        val networkErrors = boundaryCallback.errorString
        val inProcess = boundaryCallback.inProcess
        val data = LivePagedListBuilder(dataSourceFactory, PAGE_SIZE)
            .setBoundaryCallback(boundaryCallback)
            .build()
        return ResponseData(data, networkErrors, inProcess)
    }
}