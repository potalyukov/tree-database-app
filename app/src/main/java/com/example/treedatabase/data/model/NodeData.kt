package com.example.treedatabase.data.model

import java.util.UUID

data class NodeData(
    val id: String = UUID.randomUUID().toString(),
    val value: String,
    val parentId: String?,
    val deleted: Boolean
)