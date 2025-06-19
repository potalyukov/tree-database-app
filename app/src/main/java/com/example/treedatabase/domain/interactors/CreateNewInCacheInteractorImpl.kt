package com.example.treedatabase.domain.interactors

import com.example.treedatabase.domain.contracts.TreeDatabaseRepository
import com.example.treedatabase.domain.models.NodeDomain
import javax.inject.Inject

class CreateNewInCacheInteractorImpl @Inject constructor(private val repository: TreeDatabaseRepository) : CreateNewInCacheInteractor {
    override suspend fun invoke(node: NodeDomain) {
        repository.create(node)
    }
}