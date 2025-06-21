package com.example.treedatabase.domain.interactors.impl

import com.example.treedatabase.domain.contracts.TreeDatabaseRepository
import com.example.treedatabase.domain.interactors.DeleteInCacheInteractor
import javax.inject.Inject

class DeleteInCacheInteractorImpl @Inject constructor(private val repository: TreeDatabaseRepository) :
    DeleteInCacheInteractor {
    override suspend fun invoke(id: String) {
        repository.deleteInCache(id)
    }
}