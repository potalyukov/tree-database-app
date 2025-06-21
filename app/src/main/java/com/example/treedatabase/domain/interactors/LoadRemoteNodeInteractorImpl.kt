package com.example.treedatabase.domain.interactors

import com.example.treedatabase.domain.contracts.TreeDatabaseRepository
import javax.inject.Inject

class LoadRemoteNodeInteractorImpl @Inject constructor(private val repository: TreeDatabaseRepository) :
    LoadRemoteNodeInteractor {
    override suspend fun invoke(id: String) {
        repository.loadRemoteNode(id)
    }
}