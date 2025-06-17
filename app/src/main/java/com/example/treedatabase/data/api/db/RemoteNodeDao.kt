package com.example.treedatabase.data.api.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RemoteNodeDao {
    @Query("SELECT * FROM nodes")
    suspend fun getAllNodes(): List<RemoteNodeEntity>

    @Query("SELECT * FROM nodes WHERE uid = :uid")
    suspend fun getNodeById(uid: Int): RemoteNodeEntity?

    @Query("UPDATE nodes SET deleted = 1 WHERE uid = :uid")
    suspend fun deleteNodeById(uid: Int)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun applyNodes(nodes: List<RemoteNodeEntity>)

    @Query("SELECT * FROM nodes WHERE parentId = :parentId")
    suspend fun getDirectChildren(parentId: Int): List<RemoteNodeEntity>
}