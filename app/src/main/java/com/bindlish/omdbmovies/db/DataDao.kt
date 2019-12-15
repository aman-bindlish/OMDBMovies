package com.bindlish.omdbmovies.db

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.*
import com.bindlish.omdbmovies.data.DataItem

@Dao
interface DataDao {

    @Query("SELECT * FROM movies")
    fun getData(): LiveData<List<DataItem>>

    @Query("SELECT * FROM movies WHERE title LIKE :title")
    fun getDataWithTitle(title : String) : DataSource.Factory<Int, DataItem>

    @Query("SELECT * FROM movies WHERE id = :id")
    fun getDataById(id : String): LiveData<DataItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(repos: List<DataItem>): List<Long>

    @Query("DELETE FROM movies")
    fun deleteAll()

    @Transaction
    fun deleteAndInsertData(repos: List<DataItem>) {
//        deleteAll()
        insertData(repos)
    }

    @Transaction
    fun deleteAndInsertWithTimeStamp(repos: List<DataItem>) {
        deleteAndInsertData(repos.apply {
            for (data in this) {
                data.createdAt = System.currentTimeMillis()
            }
        })
    }
}