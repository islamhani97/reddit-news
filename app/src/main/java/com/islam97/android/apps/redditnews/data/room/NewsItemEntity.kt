package com.islam97.android.apps.redditnews.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.islam97.android.apps.redditnews.domain.models.NewsItem
import com.islam97.android.apps.redditnews.domain.models.Thumbnail

@Entity
data class NewsItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "thumbnailUrl") val thumbnailUrl: String?,
    @ColumnInfo(name = "thumbnailWidth") val thumbnailWidth: Int?,
    @ColumnInfo(name = "thumbnailHeight") val thumbnailHeight: Int?,
    @ColumnInfo(name = "url") val url: String,
)

fun NewsItem.toEntity(): NewsItemEntity {
    return NewsItemEntity(
        id = 0L,
        title = title,
        thumbnailUrl = thumbnail.url,
        thumbnailWidth = thumbnail.width,
        thumbnailHeight = thumbnail.height,
        url = url
    )
}

fun NewsItemEntity.toModel(): NewsItem {
    return NewsItem(
        title = title, thumbnail = Thumbnail(
            url = thumbnailUrl, width = thumbnailWidth, height = thumbnailHeight
        ), url = url
    )
}