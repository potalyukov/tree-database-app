package com.example.treedatabase.data.api

import com.example.treedatabase.domain.models.Node
import kotlinx.coroutines.flow.Flow

// Not real API. Used for remote db simulation
interface TreeDatabaseApiSecretDoor {
    fun fetchAll(depth: Int) : Flow<Node>
}