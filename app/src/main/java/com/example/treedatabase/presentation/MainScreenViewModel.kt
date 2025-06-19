package com.example.treedatabase.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.treedatabase.domain.interactors.ApplyOnRemoteInteractor
import com.example.treedatabase.domain.interactors.CreateNewInCacheInteractor
import com.example.treedatabase.domain.interactors.FetchLocalDatabaseInteractor
import com.example.treedatabase.domain.interactors.FetchRemoteDatabaseInteractor
import com.example.treedatabase.domain.interactors.ResetAllInteractor
import com.example.treedatabase.domain.models.NodeDomain
import com.example.treedatabase.presentation.mappers.PresentationNodeMapper
import com.example.treedatabase.presentation.model.NodeUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ScreenState(
    val databaseLines: List<NodeUi> = emptyList(),
    val cacheLines: List<NodeUi> = emptyList(),
    val selectedCacheLine: Int = -1,
    val selectedDatabaseLine: Int = -1
)

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val fetchRemoteDatabaseInteractor: FetchRemoteDatabaseInteractor,
    private val fetchLocalDatabaseInteractor: FetchLocalDatabaseInteractor,
    private val applyOnRemoteInteractor: ApplyOnRemoteInteractor,
    private val resetAllInteractor: ResetAllInteractor,
    private val createNewInCacheInteractor: CreateNewInCacheInteractor,
    private val mapper: PresentationNodeMapper
) : ViewModel() {

    private val _cacheFlow =
        fetchLocalDatabaseInteractor().map { list -> list.map { mapper.toUi(it) } }
    private val _remoteFlow =
        fetchRemoteDatabaseInteractor().map { list -> list.map { mapper.toUi(it) } }

    private val _combinedFlow = combine(_cacheFlow, _remoteFlow) { cache, remote ->
        screenState.value.copy(databaseLines = remote, cacheLines = cache)
    }

    private val _screenState = MutableStateFlow(ScreenState())
    val screenState: StateFlow<ScreenState> = _screenState

    init {
        viewModelScope.launch {
            _combinedFlow.collect { _screenState.value = it }
        }
    }

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

    fun create() = viewModelScope.launch {
        val parentListIndex = screenState.value.selectedCacheLine
        if (parentListIndex == -1) return@launch
        val selectedItem = screenState.value.cacheLines[parentListIndex]
        val newItem = NodeDomain(value = "VALUE", parent = selectedItem.id, deleted = false)
        createNewInCacheInteractor(newItem)
    }

    fun delete(id: Int) {}
    fun reset() = viewModelScope.launch {
        resetAllInteractor()
    }

    fun apply() {
    }

    fun load(id: Int) {}
}

