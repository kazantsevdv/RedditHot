package com.kazantsev.reddithot.repo.db


import androidx.room.Database
import androidx.room.RoomDatabase
import com.kazantsev.reddithot.model.PageKey
import com.kazantsev.reddithot.model.Post

@Database(entities = [PageKey::class, Post::class], version = 1, exportSchema = false)
abstract class CacheDatabase : RoomDatabase() {
    abstract val dao: CacheDao
}