package com.example.treedatabase.data.source.remote

import com.example.treedatabase.data.model.NodeData
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    suspend fun fetchNode(id: String): NodeData?
    suspend fun apply(nodes: List<NodeData>): List<NodeData>
    fun fetchAll(): Flow<List<NodeData>>
    suspend fun resetDatabase()
}