package com.example.treedatabase.data.source.local

import com.example.treedatabase.data.mappers.NodeMapper
import com.example.treedatabase.data.model.NodeData
import com.example.treedatabase.data.source.local.db.LocalNodeDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val localNodeDao: LocalNodeDao,
    private val mapper: NodeMapper
) :
    LocalDataSource {
    override fun fetchAll(): Flow<List<NodeData>> {
        return localNodeDao.getAllNodes().map { list -> list.map { mapper.toData(it) } }
    }

    override suspend fun addNode(nodeData: NodeData) {
        localNodeDao.applyNodes(listOf(mapper.toLocalDbEntity(nodeData)))
    }

    override suspend fun resetDatabase() {
        localNodeDao.reset()
    }
}