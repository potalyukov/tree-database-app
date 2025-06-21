package com.example.treedatabase.data.source.local.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "nodes")
data class LocalNodeEntity(
    @PrimaryKey val uid: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "value") val value: String,
    @ColumnInfo(name = "parentId") val parentId: String?,
    @ColumnInfo(name = "deleted") val deleted: Boolean
)