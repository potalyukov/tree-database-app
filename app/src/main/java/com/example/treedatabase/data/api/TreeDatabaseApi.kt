package com.example.treedatabase.data.api

import com.example.treedatabase.domain.models.Node

interface TreeDatabaseApi {
    suspend fun fetchNode(id: Int) : Node
    suspend fun apply(tree: Node)
}