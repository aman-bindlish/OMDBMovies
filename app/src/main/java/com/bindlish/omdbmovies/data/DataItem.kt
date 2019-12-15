package com.bindlish.omdbmovies.data

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "movies")
data class DataItem(
    @PrimaryKey
    @SerializedName("imdbID")
    var id: String,
    @SerializedName("Title")
    var title: String,
    @SerializedName("Year")
    var year: String,
    @SerializedName("Type")
    var type: String,
    @SerializedName("Poster")
    var poster: String?,
    var createdAt: Long
){
    constructor() : this("","","","","",0L)
}