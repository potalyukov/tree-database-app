package com.example.treedatabase.domain.interactors

import com.example.treedatabase.domain.contracts.TreeDatabaseRepository
import com.example.treedatabase.domain.models.NodeDomain
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchLocalDatabaseInteractorImpl @Inject constructor(private val repository: TreeDatabaseRepository) :
    FetchLocalDatabaseInteractor {
    override fun invoke(): Flow<List<NodeDomain>> {
        return repository.getAllLocalNodes()
    }
}