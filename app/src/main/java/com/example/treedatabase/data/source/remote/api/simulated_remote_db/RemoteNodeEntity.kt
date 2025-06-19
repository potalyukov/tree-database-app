package com.example.treedatabase.data.source.remote.api.simulated_remote_db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.treedatabase.data.model.NodeData

@Entity(tableName = "nodes")
data class RemoteNodeEntity(
    @PrimaryKey val uid: Long,
    @ColumnInfo(name = "value") val value: String,
    @ColumnInfo(name = "parentId") val parentId: Long?,
    @ColumnInfo(name = "deleted") val deleted: Boolean
)