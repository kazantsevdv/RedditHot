package com.kazantsev.reddithot.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PageKey(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val after: String?
)