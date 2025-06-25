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
            localDataSource.createOrUpdate(nodeData, true)
            nodeMapper.toDomain(nodeData)
        }
        return nodeDomain
    }

    override suspend fun applyCacheOnRemote() {
        val local = localDataSource.getAll()
        val appliedNodes = remoteDataSource.apply(local).associateBy { it.id }

        local.forEach {
            if (appliedNodes[it.id]?.deleted == true && !it.deleted) {
                localDataSource.delete(it.id)
            }
        }
    }

    override suspend fun resetCache() {
        localDataSource.resetDatabase()
    }

    override suspend fun resetRemote() {
        remoteDataSource.resetDatabase()
    }

    override suspend fun createInCache(node: NodeDomain) {
        localDataSource.createNode(nodeMapper.toData(node))
    }

    override suspend fun updateInCache(node: NodeDomain) {
        localDataSource.createOrUpdate(nodeMapper.toData(node), false)
    }

    override fun getAllLocalNodes(): Flow<List<NodeDomain>> {
        return localDataSource.fetchAll().map { list -> list.map { nodeMapper.toDomain(it) } }
    }

    override suspend fun deleteInCache(id: String) {
        localDataSource.delete(id)
    }
}