package com.example.treedatabase.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.treedatabase.domain.interactors.ApplyOnRemoteInteractor
import com.example.treedatabase.domain.interactors.CreateNewInCacheInteractor
import com.example.treedatabase.domain.interactors.DeleteInCacheInteractor
import com.example.treedatabase.domain.interactors.FetchLocalDatabaseInteractor
import com.example.treedatabase.domain.interactors.FetchRemoteDatabaseInteractor
import com.example.treedatabase.domain.interactors.LoadRemoteNodeInteractor
import com.example.treedatabase.domain.interactors.ResetInteractor
import com.example.treedatabase.domain.interactors.UpdateCacheItemInteractor
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
    val cacheLines: Map<String, NodeUi> = emptyMap(),
    val selectedCacheId: String? = null,
    val selectedRemoteId: String? = null
)

const val NewNodePrefix = "Node"

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val applyOnRemoteInteractor: ApplyOnRemoteInteractor,
    private val createNewInCacheInteractor: CreateNewInCacheInteractor,
    private val deleteInCacheInteractor: DeleteInCacheInteractor,
    private val fetchLocalDatabaseInteractor: FetchLocalDatabaseInteractor,
    private val fetchRemoteDatabaseInteractor: FetchRemoteDatabaseInteractor,
    private val loadRemoteNodeInteractor: LoadRemoteNodeInteractor,
    private val resetInteractor: ResetInteractor,
    private val updateCacheItemInteractor: UpdateCacheItemInteractor,

    private val mapper: PresentationNodeMapper
) : ViewModel() {

    private val cacheFlow =
        fetchLocalDatabaseInteractor().map { list -> list.map { mapper.toUi(it) } }
    private val remoteFlow =
        fetchRemoteDatabaseInteractor().map { list -> list.map { mapper.toUi(it) } }

    private val combinedFlow = combine(cacheFlow, remoteFlow) { cache, remote ->
        screenState.value.copy(
            databaseLines = remote,
            cacheLines = cache.associateBy { it.id }
        )
    }

    private val _screenState = MutableStateFlow(ScreenState())
    val screenState: StateFlow<ScreenState> = _screenState

    init {
        viewModelScope.launch {
            combinedFlow.collect { _screenState.value = it }
        }
    }

    fun cacheItemClick(id: String) {
        _screenState.update {
            it.copy(selectedCacheId = if (it.selectedCacheId == id) null else id, selectedRemoteId = null)
        }
    }

    fun databaseItemClick(id: String) {
        _screenState.update {
            it.copy(selectedRemoteId = if (it.selectedRemoteId == id) null else id, selectedCacheId = null)
        }
    }

    fun create(value: String) = viewModelScope.launch {
        val parentListId = screenState.value.selectedCacheId
        val selectedItem = screenState.value.cacheLines.get(parentListId) ?: return@launch

        val newItem = NodeDomain(
            value = value,
            parent = selectedItem.id,
            deleted = false
        )

        createNewInCacheInteractor(newItem)
    }

    fun edit(value: String) = viewModelScope.launch {
        val editingId = screenState.value.selectedCacheId
        val selectedItem = screenState.value.cacheLines.get(editingId) ?: return@launch

        updateCacheItemInteractor.invoke(mapper.toDomain(selectedItem.copy(value = value)))
    }

    fun deleteSelected() = screenState.value.selectedCacheId?.let {
        viewModelScope.launch {
            deleteInCacheInteractor.invoke(it)
        }
    }

    fun resetAll() = viewModelScope.launch {
        resetInteractor(resetCache = true, resetRemote = true)
        resetSelection()
    }

    fun resetCache() = viewModelScope.launch {
        resetInteractor(resetCache = true, resetRemote = false)
        resetSelection()
    }

    fun apply() = viewModelScope.launch {
        applyOnRemoteInteractor.invoke()
    }

    fun loadSelected() = screenState.value.selectedRemoteId?.let {
        viewModelScope.launch {
            loadRemoteNodeInteractor.invoke(it)
        }
    }

    fun getNewNodeName(): String {
        val nodeCounter = screenState.value.cacheLines.values
            .filter { Regex("$NewNodePrefix(\\d+)").matches(it.value) }
            .maxOfOrNull {
                it.value.split(NewNodePrefix)[1].toInt()
            } ?: 0

        return NewNodePrefix + (nodeCounter + 1)
    }

    private fun resetSelection() {
        _screenState.update { it.copy(selectedRemoteId = null, selectedCacheId = null) }
    }
}

