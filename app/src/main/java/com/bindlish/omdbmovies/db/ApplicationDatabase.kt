package com.bindlish.omdbmovies.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bindlish.omdbmovies.data.DataItem

@Database(entities = [DataItem::class], version = 1, exportSchema = false)
abstract class ApplicationDatabase : RoomDatabase() {

    abstract fun dataDao(): DataDao
}