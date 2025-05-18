package  com.islam97.android.apps.redditnews.data.datastore

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.islam97.android.apps.redditnews.domain.models.NewsItem
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.lang.reflect.Type
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class NewsDataStoreManager
@Inject constructor(@ApplicationContext context: Context, private val gson: Gson) :
    BaseDataStoreManager(context, NEWS_DATA_STORE_NAME) {

    companion object {
        private const val NEWS_DATA_STORE_NAME = "news_data_store_preferences"
        private val KEY_NEWS = stringPreferencesKey("news")
    }

    suspend fun saveNews(news: List<NewsItem>) {
        val newsAsString = gson.toJson(news)
        saveProperty(KEY_NEWS, newsAsString)
    }

    suspend fun getNews(): List<NewsItem> {
        return getProperty(KEY_NEWS).map { newsAsString ->
            newsAsString?.let {
                val type: Type = object : TypeToken<ArrayList<NewsItem?>?>() {}.type
                val newsList: ArrayList<NewsItem> = gson.fromJson(newsAsString, type)
                newsList
            } ?: listOf()
        }.first()
    }

    suspend fun deleteAllNews() {
        clear()
    }
}