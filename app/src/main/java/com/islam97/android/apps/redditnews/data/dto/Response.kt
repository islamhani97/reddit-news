package com.islam97.android.apps.redditnews.data.dto

import com.google.gson.annotations.SerializedName

data class Response<T>(
    @SerializedName("kind") val kind: String = "", @SerializedName("data") val data: T?
) {
    val isSuccessful: Boolean
        get() {
            return data?.let { true } ?: false
        }
}