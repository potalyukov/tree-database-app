package com.example.treedatabase.presentation.model

data class NodeUi(
    val id: String,
    val value: String,
    val parentId: String?,
    val deleted: Boolean,
    val depth: Int
)
