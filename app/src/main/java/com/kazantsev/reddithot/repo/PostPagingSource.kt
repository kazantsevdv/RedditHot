package com.kazantsev.reddithot.repo

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kazantsev.reddithot.model.Post
import com.kazantsev.reddithot.repo.api.DataSource
import retrofit2.HttpException
import javax.inject.Inject

class PostPagingSource(
    private val api: DataSource,
) : PagingSource<String, Post>() {
    override fun getRefreshKey(state: PagingState<String, Post>): String? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey ?: anchorPage?.nextKey
        }
    }


    override suspend fun load(params: LoadParams<String>): LoadResult<String, Post> {
        return try {
            Log.d("111","load")
            val response = api.getPost(limit = params.loadSize).body()?.data
            val posts = response?.children?.map { it.data }

            LoadResult.Page(
                data = posts ?: listOf(),
                prevKey = response?.before,
                nextKey = response?.after
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override val keyReuseSupported: Boolean = true
}