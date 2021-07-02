package com.kazantsev.reddithot.repo.api

import com.kazantsev.reddithot.model.ResponseApi
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DataSource {

    @GET("hot.json")
    suspend fun getPost(
        @Query("limit") limit: Int = 0,
        @Query("after") after: String? = null,
        @Query("before") before: String? = null
    ): Response<ResponseApi>

}
