package com.example.treedatabase.domain.interactors

import com.example.treedatabase.domain.contracts.TreeDatabaseRepository
import com.example.treedatabase.domain.mappers.TreeMapper
import com.example.treedatabase.domain.models.NodeDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class FetchRemoteDatabaseInteractorImpl @Inject constructor(
    private val repository: TreeDatabaseRepository,
    private val mapper: TreeMapper
) : FetchRemoteDatabaseInteractor {
    override fun invoke(): Flow<List<NodeDomain>> {
        val tree = repository.getAllRemoteNodes().map { list ->
            mapper.sortAsTreeInDepth(list)
        }
        return tree
    }
}