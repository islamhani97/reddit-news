package com.islam97.android.apps.redditnews.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface NewsDao {
    @Insert
    fun insertAll(news: List<NewsItemEntity>)

    @Query("SELECT * FROM NewsItemEntity")
    fun getAllNews(): List<NewsItemEntity>

    @Query("DELETE FROM NewsItemEntity")
    fun deleteAllNews()
}