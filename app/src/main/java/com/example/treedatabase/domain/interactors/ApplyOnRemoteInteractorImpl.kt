package com.example.treedatabase.domain.interactors

import com.example.treedatabase.domain.contracts.TreeDatabaseRepository
import com.example.treedatabase.domain.models.NodeDomain
import javax.inject.Inject

class ApplyOnRemoteInteractorImpl @Inject constructor(private val repository: TreeDatabaseRepository) :
    ApplyOnRemoteInteractor {
    override suspend fun invoke(nodes: List<NodeDomain>) {
        repository.apply(nodes)
    }
}