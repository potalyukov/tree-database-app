package com.example.treedatabase.domain.interactors.impl

import com.example.treedatabase.domain.contracts.TreeDatabaseRepository
import com.example.treedatabase.domain.interactors.ResetAllInteractor
import javax.inject.Inject

class ResetAllInteractorImpl @Inject constructor(private val repository: TreeDatabaseRepository) :
    ResetAllInteractor {
    override suspend fun invoke() {
        repository.resetAll()
    }
}