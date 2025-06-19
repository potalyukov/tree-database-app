package com.example.treedatabase.data.source.local.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "nodes")
data class LocalNodeEntity(
    @PrimaryKey(autoGenerate = true) val uid: Long = 0,
    @ColumnInfo(name = "value") val value: String,
    @ColumnInfo(name = "parentId") val parentId: Long?,
    @ColumnInfo(name = "deleted") val deleted: Boolean
)