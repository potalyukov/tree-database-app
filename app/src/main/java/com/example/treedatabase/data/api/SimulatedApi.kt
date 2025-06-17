package com.example.treedatabase.data.api

import com.example.treedatabase.data.api.db.RemoteNodeEntity
import com.example.treedatabase.domain.models.Node
import kotlinx.coroutines.flow.Flow

class SimulatedApi : TreeDatabaseApi, TreeDatabaseApiSecretDoor {
    override suspend fun fetchNode(id: Int): Node {
        TODO("Not yet implemented")
    }

    override suspend fun apply(tree: Node) {
        TODO("Not yet implemented")
    }

    override fun fetchAll(): Flow<RemoteNodeEntity> {
        TODO("Not yet implemented")
    }

    override fun resetDatabase() {
        TODO("Not yet implemented")
    }
}