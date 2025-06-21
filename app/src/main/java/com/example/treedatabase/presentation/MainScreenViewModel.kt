package com.example.treedatabase.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.treedatabase.domain.interactors.ApplyOnRemoteInteractor
import com.example.treedatabase.domain.interactors.CreateNewInCacheInteractor
import com.example.treedatabase.domain.interactors.DeleteInCacheInteractor
import com.example.treedatabase.domain.interactors.FetchLocalDatabaseInteractor
import com.example.treedatabase.domain.interactors.FetchRemoteDatabaseInteractor
import com.example.treedatabase.domain.interactors.LoadRemoteNodeInteractor
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
    val cacheLines: Map<String, NodeUi> = emptyMap(),
    val selectedCacheId: String? = null,
    val selectedRemoteId: String? = null
)

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val applyOnRemoteInteractor: ApplyOnRemoteInteractor,
    private val createNewInCacheInteractor: CreateNewInCacheInteractor,
    private val deleteInCacheInteractor: DeleteInCacheInteractor,
    private val fetchLocalDatabaseInteractor: FetchLocalDatabaseInteractor,
    private val fetchRemoteDatabaseInteractor: FetchRemoteDatabaseInteractor,
    private val loadRemoteNodeInteractor: LoadRemoteNodeInteractor,
    private val resetAllInteractor: ResetAllInteractor,

    private val mapper: PresentationNodeMapper
) : ViewModel() {

    private var createdNodesCounter: Int = 0

    private val _cacheFlow =
        fetchLocalDatabaseInteractor().map { list -> list.map { mapper.toUi(it) } }
    private val _remoteFlow =
        fetchRemoteDatabaseInteractor().map { list -> list.map { mapper.toUi(it) } }

    private val _combinedFlow = combine(_cacheFlow, _remoteFlow) { cache, remote ->
        screenState.value.copy(
            databaseLines = remote,
            cacheLines = cache.associateBy { it.id }
        )
    }

    private val _screenState = MutableStateFlow(ScreenState())
    val screenState: StateFlow<ScreenState> = _screenState

    init {
        viewModelScope.launch {
            _combinedFlow.collect { _screenState.value = it }
        }
    }

    fun cacheItemClick(id: String) {
        _screenState.update {
            it.copy(selectedCacheId = if (it.selectedCacheId == id) null else id)
        }
    }

    fun databaseItemClick(id: String) {
        _screenState.update {
            it.copy(selectedRemoteId = if (it.selectedRemoteId == id) null else id)
        }
    }

    fun create() = viewModelScope.launch {
        val parentListId = screenState.value.selectedCacheId
        val selectedItem = screenState.value.cacheLines.get(parentListId) ?: return@launch

        val newItem = NodeDomain(
            value = "node${createdNodesCounter++}",
            parent = selectedItem.id,
            deleted = false
        )

        createNewInCacheInteractor(newItem)
    }

    fun edit() = viewModelScope.launch {

    }

    fun deleteSelected() = screenState.value.selectedCacheId?.let {
        viewModelScope.launch {
            deleteInCacheInteractor.invoke(it)
        }
    }

    fun reset() = viewModelScope.launch {
        resetAllInteractor()
    }

    fun apply() = viewModelScope.launch {
        applyOnRemoteInteractor.invoke()
    }

    fun loadSelected() = screenState.value.selectedRemoteId?.let {
        viewModelScope.launch {
            loadRemoteNodeInteractor.invoke(it)
        }
    }
}

