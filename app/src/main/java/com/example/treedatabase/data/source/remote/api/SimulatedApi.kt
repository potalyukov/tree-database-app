package com.example.treedatabase.data.source.remote.api

import com.example.treedatabase.data.source.remote.api.simulated_remote_db.RemoteNodeDao
import com.example.treedatabase.data.mappers.NodeMapper
import com.example.treedatabase.data.model.NodeData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

class SimulatedApi @Inject constructor(
    val remoteNodeDao: RemoteNodeDao,
    val nodeMapper: NodeMapper
) : TreeDatabaseApi,
    TreeDatabaseApiSecretDoor {

    init {
        CoroutineScope(Dispatchers.IO).launch {
            val all = fetchAllSuspend()
            if (all.isEmpty()) {
               // resetDatabase()
            }
        }
    }

    override suspend fun fetchNode(id: String): NodeData? {
        return remoteNodeDao.getNodeById(id)?.let { nodeMapper.toData(it) }
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

    override suspend fun fetchAllSuspend(): List<NodeData> {
        return remoteNodeDao.getAllSuspend().map { nodeMapper.toData(it) }
    }
}