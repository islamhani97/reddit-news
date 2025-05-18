package com.islam97.android.apps.redditnews.presentation.news

import androidx.lifecycle.viewModelScope
import com.islam97.android.apps.redditnews.domain.models.NewsItem
import com.islam97.android.apps.redditnews.domain.usecase.GetNewsUseCase
import com.islam97.android.apps.redditnews.presentation.base.StateViewModel
import com.islam97.android.apps.redditnews.presentation.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel
@Inject constructor(
    private val getNewsUseCase: GetNewsUseCase
) : StateViewModel<NewsState, NewsIntent, NewsIntent.NavigationIntent>(NewsState()) {

    init {
        executeIntent(NewsIntent.GetNews(false))
    }

    override fun executeIntent(intent: NewsIntent) {
        viewModelScope.launch {
            when (intent) {
                is NewsIntent.GetNews -> {
                    getNews(intent.isRefreshing)
                }

                is NewsIntent.NavigationIntent -> {
                    mutableNavigationActionsFlow.emit(intent)
                }
            }
        }
    }

    private fun getNews(isRefreshing: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            getNewsUseCase.invoke().collectLatest { result ->
                when (result) {
                    is Result.Loading -> {
                        mutableState.value = mutableState.value.copy(
                            isLoading = !isRefreshing, isRefreshing = isRefreshing
                        )
                    }

                    is Result.Success<*> -> {
                        mutableState.value = mutableState.value.copy(
                            isLoading = false,
                            isRefreshing = false,
                            errorMessage = null,
                            news = result.data as List<NewsItem>,
                        )
                    }

                    is Result.Error -> {
                        mutableState.value = mutableState.value.copy(
                            isLoading = false,
                            isRefreshing = false,
                            errorMessage = result.errorMessage ?: "",
                        )
                    }
                }
            }
        }
    }
}

data class NewsState(
    val isLoading: Boolean = true,
    val isRefreshing: Boolean = false,
    val errorMessage: String? = null,
    val news: List<NewsItem>? = null
)

sealed class NewsIntent {
    data class GetNews(val isRefreshing: Boolean) : NewsIntent()
    sealed class NavigationIntent : NewsIntent() {
        data class NavigateToDetails(val item: NewsItem) : NavigationIntent()
    }
}