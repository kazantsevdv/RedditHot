package com.kazantsev.reddithot.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Post(
    @PrimaryKey
    val name: String,
    val title: String,
    val score: Int,
    val num_comments: Int?
)
