package com.kazantsev.reddithot.repo

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.kazantsev.reddithot.model.Post
import com.kazantsev.reddithot.repo.api.DataSource
import com.kazantsev.reddithot.repo.db.CacheDatabase
import kotlinx.coroutines.flow.Flow

class RedditRepoImp(private val api: DataSource, private val db: CacheDatabase) : RedditRepo {

    @ExperimentalPagingApi
    override fun getPosts(): Flow<PagingData<Post>> {
        return Pager(
            PagingConfig(pageSize = 20, enablePlaceholders = false,prefetchDistance = 3),
            remoteMediator = PostRemoteMediator(api, db),
            pagingSourceFactory = { db.dao.getPosts() }
        ).flow

    }
}