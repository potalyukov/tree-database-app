package com.example.treedatabase.domain.contracts

import com.example.treedatabase.domain.models.NodeDomain
import kotlinx.coroutines.flow.Flow

interface TreeDatabaseRepository {
    fun getAllRemoteNodes() : Flow<List<NodeDomain>>
    fun getAllLocalNodes() : Flow<List<NodeDomain>>
    suspend fun resetAll()

    suspend fun loadRemoteNode(id: String): NodeDomain?
    suspend fun apply()

    suspend fun create(node: NodeDomain)
    suspend fun deleteInCache(id: String)
}