package com.example.treedatabase.data.source.remote.api

import com.example.treedatabase.data.model.NodeData

interface TreeDatabaseApi {
    suspend fun fetchNode(id: String) : NodeData?
    suspend fun apply(nodes: List<NodeData>): List<NodeData>
}