package com.bindlish.omdbmovies.data

import androidx.lifecycle.LiveData
import androidx.paging.PagedList

data class ResponseData(
    val data: LiveData<PagedList<DataItem>>,
    val errorString: LiveData<String>,
    val inProcess: LiveData<Boolean>
)