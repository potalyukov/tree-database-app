package com.example.treedatabase.data.repository

import com.example.treedatabase.data.mappers.NodeMapper
import com.example.treedatabase.data.model.NodeData
import com.example.treedatabase.data.source.local.LocalDataSource
import com.example.treedatabase.data.source.remote.RemoteDataSource
import com.example.treedatabase.data.source.remote.api.simulated_remote_db.RemoteNodeEntity
import com.example.treedatabase.domain.contracts.TreeDatabaseRepository
import com.example.treedatabase.domain.models.NodeDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TreeDatabaseRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val nodeMapper: NodeMapper
) : TreeDatabaseRepository {

    override fun getAllRemoteNodes(): Flow<List<NodeDomain>> {
        return remoteDataSource.fetchAll().map { list -> list.map { nodeMapper.toDomain(it) } }
    }

    override suspend fun loadRemoteNode(id: Int): NodeDomain {
        val nodeData = remoteDataSource.fetchNode(id)
        localDataSource.addNode(nodeData)
        return nodeMapper.toDomain(nodeData)
    }

    override suspend fun apply(nodes: List<NodeDomain>) {
        remoteDataSource.apply(nodes.map { nodeMapper.toData(it) })
    }

    override suspend fun resetAll() {
        remoteDataSource.resetDatabase()
        remoteDataSource.apply(listOf(NodeData(0, "root", null, false)))
        localDataSource.resetDatabase()
        localDataSource.addNode(NodeData(0, "root", null, false))

    }

    override suspend fun create(node: NodeDomain) {
        localDataSource.addNode(nodeMapper.toData(node))
    }

    override fun getAllLocalNodes(): Flow<List<NodeDomain>> {
        return localDataSource.fetchAll().map { list -> list.map { nodeMapper.toDomain(it) } }
    }
}