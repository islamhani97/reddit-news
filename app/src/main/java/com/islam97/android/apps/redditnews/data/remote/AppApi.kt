package com.islam97.android.apps.redditnews.data.remote

import com.islam97.android.apps.redditnews.data.dto.NewsResponseDto
import com.islam97.android.apps.redditnews.data.dto.Response
import retrofit2.http.GET

interface AppApi {
    @GET("r/kotlin/.json")
    suspend fun getNews(): Response<NewsResponseDto>
}