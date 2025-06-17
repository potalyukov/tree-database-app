package com.example.treedatabase.domain.contracts

import com.example.treedatabase.domain.models.Node
import kotlinx.coroutines.flow.Flow

interface TreeDatabaseRepository {
    fun getAllRemoteNodes() : Flow<Node>

    suspend fun loadRemoteNode(id: Int): Node
    suspend fun apply(root: Node)
}