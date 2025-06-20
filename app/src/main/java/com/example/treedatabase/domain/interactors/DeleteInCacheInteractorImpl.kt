package com.example.treedatabase.domain.interactors

import com.example.treedatabase.domain.contracts.TreeDatabaseRepository
import javax.inject.Inject

class DeleteInCacheInteractorImpl @Inject constructor(private val repository: TreeDatabaseRepository) :
    DeleteInCacheInteractor {
    override suspend fun invoke(id: Long) {
        repository.deleteInCache(id)
    }
}