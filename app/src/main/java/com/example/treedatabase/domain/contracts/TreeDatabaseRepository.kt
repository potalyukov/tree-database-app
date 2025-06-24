package com.example.treedatabase.domain.contracts

import com.example.treedatabase.domain.models.NodeDomain
import kotlinx.coroutines.flow.Flow

interface TreeDatabaseRepository {
    fun getAllRemoteNodes() : Flow<List<NodeDomain>>
    fun getAllLocalNodes() : Flow<List<NodeDomain>>
    suspend fun resetCache()
    suspend fun resetRemote()

    suspend fun loadRemoteNode(id: String): NodeDomain?
    suspend fun applyCacheOnRemote()

    suspend fun createInCache(node: NodeDomain)
    suspend fun updateInCache(node: NodeDomain)
    suspend fun deleteInCache(id: String)
}