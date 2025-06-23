package com.example.treedatabase.domain.interactors.impl

import com.example.treedatabase.domain.contracts.TreeDatabaseRepository
import com.example.treedatabase.domain.interactors.ResetInteractor
import javax.inject.Inject

class ResetInteractorImpl @Inject constructor(private val repository: TreeDatabaseRepository) :
    ResetInteractor {
    override suspend fun invoke(resetCache: Boolean, resetRemote: Boolean) {
        if (resetCache) {
            repository.resetCache()
        }
        if (resetRemote) {
            repository.resetRemote()
        }
    }
}