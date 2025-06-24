package com.example.treedatabase.data.source.local

import com.example.treedatabase.data.model.NodeData
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    fun fetchAll(): Flow<List<NodeData>>
    suspend fun getAll(): List<NodeData>
    suspend fun resetDatabase()
    suspend fun createNode(nodeData: NodeData)
    suspend fun createOrUpdate(nodeData: NodeData, updateRemoved: Boolean)
    suspend fun delete(id: String)
}