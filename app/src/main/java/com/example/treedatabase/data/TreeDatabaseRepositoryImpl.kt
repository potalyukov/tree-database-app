package com.example.treedatabase.data

import com.example.treedatabase.data.api.TreeDatabaseApi
import com.example.treedatabase.data.api.TreeDatabaseApiSecretDoor
import com.example.treedatabase.domain.contracts.TreeDatabaseRepository
import com.example.treedatabase.domain.models.Node
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TreeDatabaseRepositoryImpl(
    private val treeApi: TreeDatabaseApi,
    private val secretApi: TreeDatabaseApiSecretDoor
) : TreeDatabaseRepository {

    override fun getAllRemoteNodes(): Flow<Node> {
        val all = secretApi.fetchAll()
        return all.map { entity ->
            Node(entity.uid, entity.value, null)
        }
    }

    override suspend fun loadRemoteNode(id: Int): Node {
        return treeApi.fetchNode(id)
    }

    override suspend fun apply(root: Node) {

    }
}