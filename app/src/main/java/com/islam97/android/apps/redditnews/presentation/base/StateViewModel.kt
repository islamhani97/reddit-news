package com.islam97.android.apps.redditnews.presentation.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

abstract class StateViewModel<S, I, NI : I>(initialState: S) : ViewModel() {

    protected val mutableNavigationActionsFlow: MutableSharedFlow<NI> = MutableSharedFlow()
    val navigationActionsFlow: SharedFlow<NI> get() = mutableNavigationActionsFlow

    protected val mutableState: MutableStateFlow<S> = MutableStateFlow(initialState)
    val state: StateFlow<S> get() = mutableState

    abstract fun executeIntent(intent: I)
}