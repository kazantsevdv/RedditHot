package com.kazantsev.reddithot.repo

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.kazantsev.reddithot.model.PageKey
import com.kazantsev.reddithot.model.Post
import com.kazantsev.reddithot.repo.api.DataSource
import com.kazantsev.reddithot.repo.db.CacheDatabase
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class PostRemoteMediator(
    private val api: DataSource,
    private val db: CacheDatabase

) : RemoteMediator<Int, Post>() {
    override suspend fun load(loadType: LoadType, state: PagingState<Int, Post>): MediatorResult =
        when (loadType) {
            LoadType.REFRESH -> refresh(state)
            LoadType.PREPEND -> prepend()
            LoadType.APPEND -> append(state)
        }

    private suspend fun append(state: PagingState<Int, Post>): MediatorResult {

        state.pages.lastOrNull()
            ?: return MediatorResult.Success(endOfPaginationReached = true)
        return try {
            val postKey = db.dao.getPageKeys().lastOrNull()
            val apiResponse =
                api.getPost(
                    limit = state.config.pageSize,
                    after = postKey?.after
                )
            if (apiResponse.isSuccessful) {
                val response = apiResponse.body()?.data
                val posts = response?.children?.map { it.data }
                posts?.let {
                    db.dao.insertPostAndKey(
                        PageKey(0, response.after),
                        posts
                    )

                }
                MediatorResult.Success(endOfPaginationReached = posts.isNullOrEmpty())

            } else {
                MediatorResult.Error(HttpException(apiResponse))
            }
        } catch (exception: IOException) {

            MediatorResult.Error(exception)
        } catch (exception: HttpException) {

            MediatorResult.Error(exception)
        }
    }

    private fun prepend(): MediatorResult {
        return MediatorResult.Success(endOfPaginationReached = true)
    }

    private suspend fun refresh(state: PagingState<Int, Post>): MediatorResult {
        return try {
            val apiResponse = api.getPost(limit = state.config.pageSize)
            if (apiResponse.isSuccessful) {

                db.dao.deleteAll()

                val response = apiResponse.body()?.data
                val posts = response?.children?.map { it.data }
                posts?.let {
                    db.dao.insertPostAndKey(
                        PageKey(0, response.after),
                        posts
                    )

                    MediatorResult.Success(endOfPaginationReached = it.isEmpty())
                }
                MediatorResult.Success(endOfPaginationReached = false)

            } else {
                MediatorResult.Error(HttpException(apiResponse))
            }


        } catch (exception: IOException) {

            MediatorResult.Error(exception)
        } catch (exception: HttpException) {

            MediatorResult.Error(exception)

        }
    }
}

