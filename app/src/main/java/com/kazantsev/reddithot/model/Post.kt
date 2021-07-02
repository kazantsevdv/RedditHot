package com.kazantsev.reddithot.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
data class Post(
    @PrimaryKey(autoGenerate = true)
    val id: Int=0,
    @Expose val title: String,
    @Expose val score: Int,
    @Expose val num_comments: Int?
)
