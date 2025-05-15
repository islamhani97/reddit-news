package com.islam97.android.apps.redditnews.data.repositories

import android.content.Context
import com.islam97.android.apps.redditnews.R
import com.islam97.android.apps.redditnews.data.dto.Response
import com.islam97.android.apps.redditnews.presentation.utils.Result
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

open class BaseRepository {
    @Inject
    @ApplicationContext
    lateinit var context: Context

    fun <T, S> callApi(apiCall: suspend () -> Response<T>, mapper: (T?) -> S?): Flow<Result> {
        return flow {
            emit(Result.Loading)
            emit(
                try {
                    val response = apiCall()
                    if (response.isSuccessful) {
                        Result.Success(
                            context.getString(R.string.message_success), mapper(response.data)
                        )
                    } else {
                        Result.Error(context.getString(R.string.error_something_went_wrong))
                    }
                } catch (e: Throwable) {
                    Result.Error(
                        e.message ?: context.getString(R.string.error_something_went_wrong)
                    )
                }
            )
        }
    }
}