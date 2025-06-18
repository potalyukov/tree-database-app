package com.example.treedatabase.data.source.remote.api

import com.example.treedatabase.data.model.NodeData
import kotlinx.coroutines.flow.Flow


// Not real API. Used for remote db simulation
interface TreeDatabaseApiSecretDoor {
    fun fetchAll() : Flow<List<NodeData>>
    suspend fun resetDatabase()
}