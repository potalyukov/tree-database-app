package com.example.treedatabase.data.api.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "nodes")
data class RemoteNodeEntity(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "value") val value: String,
    @ColumnInfo(name = "parentId") val parentId: Int,
    @ColumnInfo(name = "deleted") val deleted: Boolean
)