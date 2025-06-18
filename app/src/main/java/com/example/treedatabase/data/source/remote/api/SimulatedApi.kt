package com.example.treedatabase.data.source.remote.api

import com.example.treedatabase.data.source.remote.api.simulated_remote_db.RemoteNodeDao
import com.example.treedatabase.data.mappers.NodeMapper
import com.example.treedatabase.data.model.NodeData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SimulatedApi @Inject constructor(
    val remoteNodeDao: RemoteNodeDao,
    val nodeMapper: NodeMapper
) : TreeDatabaseApi,
    TreeDatabaseApiSecretDoor {
    override suspend fun fetchNode(id: Int): NodeData {
        TODO("Not yet implemented")
    }

    override suspend fun apply(nodes: List<NodeData>) {
        remoteNodeDao.applyNodes(nodes.map { nodeMapper.toRemoteDbEntity(it) })
    }

    override fun fetchAll(): Flow<List<NodeData>> {
        return remoteNodeDao.getAllNodes().map { it.map { nodeMapper.toData(it) } }
    }

    override suspend fun resetDatabase() {
        remoteNodeDao.reset()
    }
}