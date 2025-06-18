package com.example.treedatabase.data.source.remote.api.simulated_remote_db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.treedatabase.data.source.remote.api.simulated_remote_db.RemoteNodeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RemoteNodeDao {
    @Query("SELECT * FROM nodes")
    fun getAllNodes(): Flow<List<RemoteNodeEntity>>

    @Query("SELECT * FROM nodes WHERE uid = :uid")
    suspend fun getNodeById(uid: Int): RemoteNodeEntity?

    @Query("UPDATE nodes SET deleted = 1 WHERE uid = :uid")
    suspend fun deleteNodeById(uid: Int)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun applyNodes(nodes: List<RemoteNodeEntity>)

    @Query("SELECT * FROM nodes WHERE parentId = :parentId")
    suspend fun getDirectChildren(parentId: Int): List<RemoteNodeEntity>

    @Query("DELETE FROM nodes")
    suspend fun reset()
}