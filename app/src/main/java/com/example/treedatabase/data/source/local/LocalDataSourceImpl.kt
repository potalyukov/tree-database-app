package com.example.treedatabase.data.source.local

import com.example.treedatabase.data.mappers.NodeMapper
import com.example.treedatabase.data.model.NodeData
import com.example.treedatabase.data.source.local.db.LocalNodeDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.UUID
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val localNodeDao: LocalNodeDao,
    private val mapper: NodeMapper
) : LocalDataSource {
    override fun fetchAll(): Flow<List<NodeData>> {
        return localNodeDao.getAllNodes().map { list -> list.map { mapper.toData(it) } }
    }

    override suspend fun createNode(nodeData: NodeData) {
        localNodeDao.createNode(
            mapper.toLocalDbEntity(
                nodeData.copy(id = UUID.randomUUID().toString())
            )
        )
    }

    override suspend fun createOrUpdate(nodeData: NodeData, updateRemoved: Boolean) {
        if (!updateRemoved) {
            localNodeDao.applyNodeIfNotDeleted(mapper.toLocalDbEntity(nodeData))
        } else {
            localNodeDao.applyNodeWithUndeleteChain(mapper.toLocalDbEntity(nodeData))
        }
    }

    override suspend fun resetDatabase() {
        localNodeDao.clearAllNodes()
    }

    override suspend fun getAll(): List<NodeData> {
        return localNodeDao.getAllSuspend().map { mapper.toData(it) }
    }

    override suspend fun delete(id: String) {
        localNodeDao.deleteNodeRecursively(id)
    }
}