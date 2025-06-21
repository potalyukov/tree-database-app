package com.example.treedatabase.domain.interactors.impl

import com.example.treedatabase.domain.contracts.TreeDatabaseRepository
import com.example.treedatabase.domain.interactors.LoadRemoteNodeInteractor
import javax.inject.Inject

class LoadRemoteNodeInteractorImpl @Inject constructor(private val repository: TreeDatabaseRepository) :
    LoadRemoteNodeInteractor {
    override suspend fun invoke(id: String) {
        repository.loadRemoteNode(id)
    }
}