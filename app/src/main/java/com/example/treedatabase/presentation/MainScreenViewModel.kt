package com.example.treedatabase.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.treedatabase.domain.interactors.ApplyOnRemoteInteractor
import com.example.treedatabase.domain.interactors.FetchRemoteDatabaseInteractor
import com.example.treedatabase.domain.interactors.ResetAllInteractor
import com.example.treedatabase.domain.models.NodeDomain
import com.example.treedatabase.presentation.mappers.PresentationNodeMapper
import com.example.treedatabase.presentation.model.NodeUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

data class ScreenState(
    val databaseLines: Flow<List<NodeUi>>,
    val cacheLines: List<String>,
    val selectedCacheLine: Int = -1,
    val selectedDatabaseLine: Int = -1
)

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val fetchRemoteDatabaseInteractor: FetchRemoteDatabaseInteractor,
    private val applyOnRemoteInteractor: ApplyOnRemoteInteractor,
    private val resetAllInteractor: ResetAllInteractor,
    private val mapper: PresentationNodeMapper
) :
    ViewModel() {
    private val initialState: ScreenState = ScreenState(
        cacheLines = listOf("*"),
        databaseLines = fetchRemoteDatabaseInteractor.invoke()
            .map { list -> list.map { mapper.toUi(it) } }
    )

    //val allNodes= fetchRemoteDatabaseInteractor().map { list -> list.map { mapper.toUi(it) } }

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

    fun create() {
        addRandomRemote()
    }

    fun delete(id: Int) {}
    fun reset() = viewModelScope.launch {
        resetAllInteractor()
    }

    fun apply() {
    }

    fun addRandomRemote() {
        val node = NodeDomain(
            id = Random.nextInt(),
            value = "val ${Random.nextInt()}",
            parent = null,
            deleted = false
        )
        viewModelScope.launch {
            applyOnRemoteInteractor.invoke(listOf(node))
        }
    }

    fun load(id: Int) {}
}

