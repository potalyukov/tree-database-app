package com.example.treedatabase.presentation

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

sealed class ScreenState {
    object LoadingState: ScreenState()
    data class LoadedState(val databaseLines: List<String>, val cacheLines: List<String>) : ScreenState()
}

class MainScreenViewModel : ViewModel() {
    private val _screenState = MutableStateFlow<ScreenState>(ScreenState.LoadingState)
    val screenState: StateFlow<ScreenState> = _screenState

    fun Create() {}
    fun Delete(id: Int) {}
    fun Reset() {}
    fun Apply() {}
    fun Load(id: Int) {}
}

