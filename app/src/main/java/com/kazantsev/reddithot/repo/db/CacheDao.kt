package com.kazantsev.reddithot.repo.db

import androidx.paging.PagingSource
import androidx.room.*
import com.kazantsev.reddithot.model.PageKey
import com.kazantsev.reddithot.model.Post

@Dao
abstract class CacheDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertKey(pageKey: PageKey)

    @Query("SELECT * FROM PageKey")
    abstract suspend fun getPageKeys(): List<PageKey>

    @Query(
        "DELETE  FROM PageKey"
    )
    abstract suspend fun deleteAllKey()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertPosts(posts: List<Post>)

    @Query("SELECT * FROM Post")
    abstract fun getPosts(): PagingSource<Int, Post>

    @Query(
        "DELETE  FROM Post"
    )
    abstract suspend fun deleteAll()

    @Transaction
    open suspend fun insertPostAndKey(pageKey: PageKey, posts: List<Post>) {
        insertKey(pageKey)
        insertPosts(posts)
    }
}