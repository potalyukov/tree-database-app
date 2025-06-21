package com.example.treedatabase.domain.models

import java.util.UUID

data class NodeDomain(
    val id: String = UUID.randomUUID().toString(),
    val value: String,
    val parent: String?,
    val deleted: Boolean,
    val depth: Int = 0
)