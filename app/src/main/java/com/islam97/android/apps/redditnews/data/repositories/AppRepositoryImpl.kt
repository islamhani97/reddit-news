package com.islam97.android.apps.redditnews.data.repositories

import com.islam97.android.apps.redditnews.data.dto.toNewsModelList
import com.islam97.android.apps.redditnews.data.remote.AppApi
import com.islam97.android.apps.redditnews.data.room.AppDatabase
import com.islam97.android.apps.redditnews.data.room.toEntity
import com.islam97.android.apps.redditnews.data.room.toModel
import com.islam97.android.apps.redditnews.domain.models.NewsItem
import com.islam97.android.apps.redditnews.domain.repositories.AppRepository
import com.islam97.android.apps.redditnews.presentation.utils.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepositoryImpl
@Inject constructor(private val appApi: AppApi, private val appDatabase: AppDatabase) :
    BaseRepository(), AppRepository {
    override suspend fun getNews(): Flow<Result> {
        return callApi(apiCall = { appApi.getNews() }) { it?.toNewsModelList() }
    }

    override suspend fun getAllNewsFromDatabase(): List<NewsItem> {
        return appDatabase.newsDao().getAllNews().map { it.toModel() }
    }

    override suspend fun saveNewsInDataBase(news: List<NewsItem>) {
        appDatabase.newsDao().insertAll(news.map { it.toEntity() })
    }

    override suspend fun deleteAllNewsFromDatabase() {
        appDatabase.newsDao().deleteAllNews()
    }
}