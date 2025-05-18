package com.islam97.android.apps.redditnews.data.dto

import com.google.gson.annotations.SerializedName
import com.islam97.android.apps.redditnews.domain.models.NewsItem
import com.islam97.android.apps.redditnews.domain.models.Thumbnail

data class NewsResponseDto(
    @SerializedName("after") val after: String?,
    @SerializedName("before") val before: Any?,
    @SerializedName("children") val children: List<NewsItemDto>?,
    @SerializedName("dist") val dist: Int?,
    @SerializedName("geo_filter") val geoFilter: Any?,
    @SerializedName("modhash") val modhash: String?
)

data class NewsItemDto(
    @SerializedName("data") val `data`: NewsItemDataDto?, @SerializedName("kind") val kind: String?
)

data class NewsItemDataDto(
    @SerializedName("media") val media: NewsItemMediaDto?,
    @SerializedName("secure_media") val secureMedia: NewsItemMediaDto?,
    @SerializedName("thumbnail") val thumbnail: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("url") val url: String?,
)

data class NewsItemMediaDto(
    @SerializedName("oembed") val oembed: Oembed?, @SerializedName("type") val type: String?
)

data class Oembed(
    @SerializedName("author_name") val authorName: String?,
    @SerializedName("author_url") val authorUrl: String?,
    @SerializedName("height") val height: Int?,
    @SerializedName("html") val html: String?,
    @SerializedName("provider_name") val providerName: String?,
    @SerializedName("provider_url") val providerUrl: String?,
    @SerializedName("thumbnail_height") val thumbnailHeight: Int?,
    @SerializedName("thumbnail_url") val thumbnailUrl: String?,
    @SerializedName("thumbnail_width") val thumbnailWidth: Int?,
    @SerializedName("title") val title: String?,
    @SerializedName("type") val type: String?,
    @SerializedName("version") val version: String?,
    @SerializedName("width") val width: Int?
)

fun NewsResponseDto.toNewsModelList(): List<NewsItem>? {
    return children?.map {
        it.toNewsItemModel()
    }
}

private fun NewsItemDto.toNewsItemModel(): NewsItem {

    return NewsItem(
        title = data?.title ?: "", thumbnail = when {
            !data?.thumbnail.isNullOrBlank() -> {
                Thumbnail(
                    url = data?.thumbnail, width = null, height = null
                )
            }

            !data?.media?.oembed?.thumbnailUrl.isNullOrBlank() -> {
                Thumbnail(
                    url = data?.media?.oembed?.thumbnailUrl,
                    width = data?.media?.oembed?.thumbnailWidth,
                    height = data?.media?.oembed?.thumbnailHeight
                )
            }

            !data?.secureMedia?.oembed?.thumbnailUrl.isNullOrBlank() -> {
                Thumbnail(
                    url = data?.secureMedia?.oembed?.thumbnailUrl,
                    width = data?.secureMedia?.oembed?.thumbnailWidth,
                    height = data?.secureMedia?.oembed?.thumbnailHeight
                )
            }

            else -> Thumbnail(url = null, width = null, height = null)
        }, url = data?.url ?: ""
    )
}