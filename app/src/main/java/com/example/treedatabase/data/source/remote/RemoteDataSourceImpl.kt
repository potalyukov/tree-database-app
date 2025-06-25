package com.example.treedatabase.data.source.remote

import com.example.treedatabase.data.model.NodeData
import com.example.treedatabase.data.source.remote.api.TreeDatabaseApi
import com.example.treedatabase.data.source.remote.api.TreeDatabaseApiSecretDoor
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val treeApi: TreeDatabaseApi,
    private val secretApi: TreeDatabaseApiSecretDoor
) : RemoteDataSource {
    override suspend fun fetchNode(id: String): NodeData? {
        return treeApi.fetchNode(id)
    }

    override suspend fun apply(nodes: List<NodeData>): List<NodeData> {
        return treeApi.apply(nodes)
    }

    override fun fetchAll(): Flow<List<NodeData>> {
        return secretApi.fetchAll()
    }

    override suspend fun resetDatabase() {
        secretApi.resetDatabase()
    }
}