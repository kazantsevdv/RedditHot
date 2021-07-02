package com.kazantsev.reddithot.di


import androidx.room.Room
import com.kazantsev.reddithot.App
import com.kazantsev.reddithot.repo.db.CacheDatabase

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DbModule {
    @Singleton
    @Provides
    fun provideDatabase(app: App): CacheDatabase =
        Room.databaseBuilder(app, CacheDatabase::class.java, "db.db")
            .fallbackToDestructiveMigration()
            .build()
}