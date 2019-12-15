package com.bindlish.omdbmovies.data

import com.bindlish.omdbmovies.di.ApiModule
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface DataApi {

    @GET("?apiKey=" + ApiModule.API_KEY)
    fun fetchMovies(@Query("s") search : String,
                    @Query("page") page : Int = 1): Single<DataResponse>
}