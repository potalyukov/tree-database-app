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

    override suspend fun loadRemoteNode(id: String): NodeDomain? {
        val nodeData = remoteDataSource.fetchNode(id)
        val nodeDomain = nodeData?.let {
            localDataSource.addNode(nodeData)
            nodeMapper.toDomain(nodeData)
        }
        return nodeDomain
    }

    override suspend fun apply() {
        val local = localDataSource.getAll()
        remoteDataSource.apply(local)
    }

    override suspend fun resetAll() {
        remoteDataSource.resetDatabase()
        localDataSource.resetDatabase()
    }

    override suspend fun create(node: NodeDomain) {
        localDataSource.addNode(nodeMapper.toData(node))
    }

    override fun getAllLocalNodes(): Flow<List<NodeDomain>> {
        return localDataSource.fetchAll().map { list -> list.map { nodeMapper.toDomain(it) } }
    }

    override suspend fun deleteInCache(id: String) {
        localDataSource.delete(id)
    }
}