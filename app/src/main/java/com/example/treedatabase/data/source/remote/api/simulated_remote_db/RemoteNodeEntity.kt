package com.example.treedatabase.data.source.remote.api.simulated_remote_db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "nodes")
data class RemoteNodeEntity(
    @PrimaryKey val uid: String,
    @ColumnInfo(name = "value") val value: String,
    @ColumnInfo(name = "parentId") val parentId: String?,
    @ColumnInfo(name = "deleted") val deleted: Boolean
)