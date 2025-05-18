package com.islam97.android.apps.redditnews.data.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

open class BaseDataStoreManager(context: Context, dateStoreName: String) {

    private val Context.dataStore by preferencesDataStore(name = dateStoreName)
    private val dataStore: DataStore<Preferences> = context.dataStore

    protected suspend fun <T> saveProperty(key: Preferences.Key<T>, value: T) {
        dataStore.edit { preferences -> preferences[key] = value }
    }

    protected fun <T> getProperty(key: Preferences.Key<T>): Flow<T?> {
        return dataStore.data.map { preferences -> preferences[key] }
    }

    protected suspend fun clear() {
        dataStore.edit { preferences -> preferences.clear() }
    }
}