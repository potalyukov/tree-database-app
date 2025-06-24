package com.example.treedatabase.domain.interactors.impl

import com.example.treedatabase.domain.contracts.TreeDatabaseRepository
import com.example.treedatabase.domain.interactors.UpdateCacheItemInteractor
import com.example.treedatabase.domain.models.NodeDomain
import javax.inject.Inject

class UpdateCacheItemInteractorImpl @Inject constructor(private val repository: TreeDatabaseRepository):
    UpdateCacheItemInteractor {
    override suspend fun invoke(updatedNode: NodeDomain) {
        repository.updateInCache(updatedNode)
    }
}