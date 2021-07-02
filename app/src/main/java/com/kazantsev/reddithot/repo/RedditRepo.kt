package com.kazantsev.reddithot.repo

import androidx.paging.PagingData
import com.kazantsev.reddithot.model.Post
import kotlinx.coroutines.flow.Flow

interface RedditRepo {
     fun getPosts(): Flow<PagingData<Post>>
}