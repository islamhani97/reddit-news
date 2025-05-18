package com.islam97.android.apps.redditnews.data.repositories

import com.islam97.android.apps.redditnews.data.dto.toNewsModelList
import com.islam97.android.apps.redditnews.data.remote.AppApi
import com.islam97.android.apps.redditnews.domain.repositories.AppRepository
import com.islam97.android.apps.redditnews.presentation.utils.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepositoryImpl
@Inject constructor(private val appApi: AppApi) : BaseRepository(), AppRepository {
    override suspend fun getNews(): Flow<Result> {
        return callApi(apiCall = { appApi.getNews() }) { it?.toNewsModelList() }
    }
}