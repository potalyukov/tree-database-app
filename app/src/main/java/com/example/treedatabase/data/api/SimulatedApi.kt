package com.example.treedatabase.data.api

import com.example.treedatabase.domain.models.Node
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SimulatedApi : TreeDatabaseApi, TreeDatabaseApiSecretDoor {
    override suspend fun fetchNode(id: String): Node {
        // todo
        return Node("", "", null)
    }

    override suspend fun apply(tree: Node) {
        // todo
    }

    override fun fetchAll(depth: Int): Flow<Node> = flow {
        // todo
    }
}