package com.example.treedatabase.data.source.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.treedatabase.data.source.remote.api.simulated_remote_db.RemoteNodeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalNodeDao {
    @Query("SELECT * FROM nodes")
    fun getAllNodes(): Flow<List<LocalNodeEntity>>

    @Query("SELECT * FROM nodes WHERE uid = :uid")
    suspend fun getNodeById(uid: Int): LocalNodeEntity?

    @Query("UPDATE nodes SET deleted = 1 WHERE uid = :uid")
    suspend fun deleteNodeById(uid: Int)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun applyNodes(nodes: List<LocalNodeEntity>)

    @Query("SELECT * FROM nodes WHERE parentId = :parentId")
    suspend fun getDirectChildren(parentId: Int): List<LocalNodeEntity>

    @Query("DELETE FROM nodes")
    suspend fun reset()
}