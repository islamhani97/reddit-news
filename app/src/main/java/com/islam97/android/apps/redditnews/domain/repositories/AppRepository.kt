package com.islam97.android.apps.redditnews.domain.repositories

import com.islam97.android.apps.redditnews.domain.models.NewsItem
import com.islam97.android.apps.redditnews.presentation.utils.Result
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    suspend fun getNews(): Flow<Result>
    suspend fun getAllNewsFromDataStore(): List<NewsItem>
    suspend fun saveNewsInDataStore(news: List<NewsItem>)
    suspend fun deleteAllNewsFromDataStore()
    suspend fun getAllNewsFromDatabase(): List<NewsItem>
    suspend fun saveNewsInDataBase(news: List<NewsItem>)
    suspend fun deleteAllNewsFromDatabase()
}