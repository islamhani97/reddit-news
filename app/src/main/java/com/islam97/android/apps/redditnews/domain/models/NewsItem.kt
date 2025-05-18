package com.islam97.android.apps.redditnews.domain.models

data class NewsItem(
    val title: String, val thumbnail: Thumbnail, val url: String
)

data class Thumbnail(
    val url: String?, val width: Int?, val height: Int?
)