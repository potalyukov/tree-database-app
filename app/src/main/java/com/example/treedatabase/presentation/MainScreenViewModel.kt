package com.example.treedatabase.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

data class ScreenState(
    val databaseLines: List<String>,
    val cacheLines: List<String>,
    val selectedCacheLine: Int = -1,
    val selectedDatabaseLine: Int = -1
)

@HiltViewModel
class MainScreenViewModel : ViewModel() {
    private val initialState: ScreenState = ScreenState(
        cacheLines = listOf("*nodeA", "   nodeB", "   nodeC", "     nodeD", "     nodeE"),
        databaseLines = listOf("*nodeA", "   nodeB", "   nodeC", "     nodeD", "     nodeE"),
    )

    private val _screenState = MutableStateFlow(initialState)
    val screenState: StateFlow<ScreenState> = _screenState

    fun cacheItemClick(index: Int) {
        _screenState.update {
            it.copy(selectedCacheLine = if (it.selectedCacheLine == index) -1 else index)
        }
    }

    fun databaseItemClick(index: Int) {
        _screenState.update {
            it.copy(selectedDatabaseLine = if (it.selectedDatabaseLine == index) -1 else index)
        }
    }

    fun create() {}
    fun delete(id: Int) {}
    fun reset() {}
    fun apply() {}
    fun load(id: Int) {}
}

