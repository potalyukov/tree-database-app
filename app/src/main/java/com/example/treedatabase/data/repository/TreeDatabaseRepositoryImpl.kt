package com.example.treedatabase.data.repository

import com.example.treedatabase.data.mappers.NodeMapper
import com.example.treedatabase.data.source.local.LocalDataSource
import com.example.treedatabase.data.source.remote.RemoteDataSource
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
        localDataSource.resetDatabase()
    }
}