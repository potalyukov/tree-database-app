package com.example.treedatabase.data.source.remote.api.simulated_remote_db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface RemoteNodeDao {
    @Query("SELECT * FROM nodes")
    fun getAllNodes(): Flow<List<RemoteNodeEntity>>

    @Query("SELECT * FROM nodes")
    suspend fun getAllSuspend(): List<RemoteNodeEntity>

    @Query("SELECT * FROM nodes WHERE uid = :uid")
    suspend fun getNodeById(uid: String): RemoteNodeEntity?

    @Query("UPDATE nodes SET deleted = 1 WHERE uid = :uid")
    suspend fun deleteNodeById(uid: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun applyNodes(nodes: List<RemoteNodeEntity>)

    @Query("SELECT * FROM nodes WHERE parentId = :parentId")
    suspend fun getDirectChildren(parentId: String): List<RemoteNodeEntity>

    @Query("DELETE FROM nodes")
    suspend fun clearAllNodes()

    @Insert
    suspend fun insertNode(node: RemoteNodeEntity)

    @Transaction
    suspend fun reset() {
        clearAllNodes()
        insertNode(
            RemoteNodeEntity(
                uid = "root",
                value = "root",
                parentId = null,
                deleted = false
            )
        )
    }
}