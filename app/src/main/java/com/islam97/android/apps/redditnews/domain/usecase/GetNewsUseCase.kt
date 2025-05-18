package com.islam97.android.apps.redditnews.domain.usecase

import com.islam97.android.apps.redditnews.domain.models.NewsItem
import com.islam97.android.apps.redditnews.domain.repositories.AppRepository
import com.islam97.android.apps.redditnews.presentation.utils.AppConnectivityManager
import com.islam97.android.apps.redditnews.presentation.utils.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetNewsUseCase
@Inject constructor(
    private val appConnectivityManager: AppConnectivityManager,
    private val appRepository: AppRepository
) {
    operator fun invoke(): Flow<Result> {
        return flow {
            if (appConnectivityManager.isConnectedToInternet()) {
                try {
                    appRepository.getNews().collect {
                        when (it) {
                            is Result.Loading -> {
                                emit(it)
                            }

                            is Result.Success<*> -> {
                                appRepository.deleteAllNewsFromDataStore()
                                val news = it.data as List<NewsItem>
                                appRepository.saveNewsInDataStore(news)
                                emit(it)
                            }

                            is Result.Error -> {
                                emit(it)
                            }
                        }
                    }
                } catch (t: Throwable) {
                    emit(Result.Error(""))
                }

            } else {
                emit(Result.Loading)
                emit(Result.Success("", appRepository.getAllNewsFromDataStore()))
            }
        }
    }
}