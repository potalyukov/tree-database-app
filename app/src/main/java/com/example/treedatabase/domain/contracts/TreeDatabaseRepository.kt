package com.example.treedatabase.domain.contracts

import com.example.treedatabase.domain.models.NodeDomain
import kotlinx.coroutines.flow.Flow

interface TreeDatabaseRepository {
    fun getAllRemoteNodes() : Flow<List<NodeDomain>>
    suspend fun resetAll()

    suspend fun loadRemoteNode(id: Int): NodeDomain
    suspend fun apply(nodes: List<NodeDomain>)
}