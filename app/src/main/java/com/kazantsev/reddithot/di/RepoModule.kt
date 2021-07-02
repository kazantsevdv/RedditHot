package com.kazantsev.reddithot.di


import androidx.room.Room
import com.kazantsev.reddithot.App
import com.kazantsev.reddithot.repo.RedditRepo
import com.kazantsev.reddithot.repo.RedditRepoImp
import com.kazantsev.reddithot.repo.api.DataSource
import com.kazantsev.reddithot.repo.db.CacheDatabase

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepoModule {
    @Singleton
    @Provides
    fun provideRepo(api: DataSource, db: CacheDatabase): RedditRepo =RedditRepoImp(api,db)

}