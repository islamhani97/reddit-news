package com.islam97.android.apps.redditnews.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [NewsItemEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun newsDao(): NewsDao
}