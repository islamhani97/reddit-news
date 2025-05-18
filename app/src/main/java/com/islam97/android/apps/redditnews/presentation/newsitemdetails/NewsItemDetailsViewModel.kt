package com.islam97.android.apps.redditnews.presentation.newsitemdetails

import androidx.lifecycle.viewModelScope
import com.islam97.android.apps.redditnews.presentation.base.StateViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsItemDetailsViewModel
@Inject constructor() :
    StateViewModel<NewsItemDetailsState, NewsItemDetailsIntent, NewsItemDetailsIntent.NavigationIntent>(
        NewsItemDetailsState()
    ) {

    override fun executeIntent(intent: NewsItemDetailsIntent) {
        viewModelScope.launch {
            when (intent) {
                is NewsItemDetailsIntent.SetLoadingState -> {
                    mutableState.value = mutableState.value.copy(isLoading = intent.isLoading)
                }

                is NewsItemDetailsIntent.NavigationIntent -> {
                    mutableNavigationActionsFlow.emit(intent)
                }
            }
        }
    }
}

data class NewsItemDetailsState(val isLoading: Boolean = true)

sealed class NewsItemDetailsIntent {
    data class SetLoadingState(val isLoading: Boolean) : NewsItemDetailsIntent()
    sealed class NavigationIntent : NewsItemDetailsIntent() {
        data object Back : NavigationIntent()
    }
}