package com.islam97.android.apps.redditnews.domain.usecase

import com.islam97.android.apps.redditnews.domain.repositories.AppRepository
import com.islam97.android.apps.redditnews.presentation.utils.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNewsUseCase
@Inject constructor(
    private val appRepository: AppRepository
) {
    suspend operator fun invoke(): Flow<Result> {
        return appRepository.getNews()
    }
}