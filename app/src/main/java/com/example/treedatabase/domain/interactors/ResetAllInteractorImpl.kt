package com.example.treedatabase.domain.interactors

import com.example.treedatabase.domain.contracts.TreeDatabaseRepository
import javax.inject.Inject

class ResetAllInteractorImpl @Inject constructor(private val repository: TreeDatabaseRepository) :
    ResetAllInteractor {
    override suspend fun invoke() {
        repository.resetAll()
    }
}