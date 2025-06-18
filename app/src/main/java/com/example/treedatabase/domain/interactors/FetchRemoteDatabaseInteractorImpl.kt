package com.example.treedatabase.domain.interactors

import com.example.treedatabase.domain.contracts.TreeDatabaseRepository
import com.example.treedatabase.domain.models.NodeDomain
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class FetchRemoteDatabaseInteractorImpl @Inject constructor(val repository: TreeDatabaseRepository) :
    FetchRemoteDatabaseInteractor {
    override fun invoke(): Flow<List<NodeDomain>> {
        return repository.getAllRemoteNodes()
    }
}