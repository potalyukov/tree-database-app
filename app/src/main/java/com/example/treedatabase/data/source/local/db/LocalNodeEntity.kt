package com.example.treedatabase.data.source.local.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "nodes")
data class LocalNodeEntity(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "value") val value: String,
    @ColumnInfo(name = "parentId") val parentId: Int?,
    @ColumnInfo(name = "deleted") val deleted: Boolean
)