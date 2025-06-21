package com.example.treedatabase.domain.interactors.impl

import com.example.treedatabase.domain.contracts.TreeDatabaseRepository
import com.example.treedatabase.domain.interactors.ApplyOnRemoteInteractor
import javax.inject.Inject

class ApplyOnRemoteInteractorImpl @Inject constructor(private val repository: TreeDatabaseRepository) :
    ApplyOnRemoteInteractor {
    override suspend fun invoke() {
        repository.apply()
    }
}