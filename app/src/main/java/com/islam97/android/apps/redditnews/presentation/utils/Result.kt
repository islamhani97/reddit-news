package com.islam97.android.apps.redditnews.presentation.utils

sealed class Result {
    data object Loading : Result()
    data class Success<T>(val message: String?, val data: T) : Result()
    data class Error(val errorMessage: String?) : Result()
}