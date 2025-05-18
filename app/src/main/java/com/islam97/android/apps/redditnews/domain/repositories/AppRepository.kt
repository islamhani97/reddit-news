package com.islam97.android.apps.redditnews.domain.repositories

import com.islam97.android.apps.redditnews.presentation.utils.Result
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    suspend fun getNews(): Flow<Result>
}