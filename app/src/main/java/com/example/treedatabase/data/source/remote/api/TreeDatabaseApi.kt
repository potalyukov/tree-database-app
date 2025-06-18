package com.example.treedatabase.data.source.remote.api

import com.example.treedatabase.data.model.NodeData

interface TreeDatabaseApi {
    suspend fun fetchNode(id: Int) : NodeData
    suspend fun apply(nodes: List<NodeData>)
}