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

        val rootNode = RemoteNodeEntity(
            uid = "root",
            value = "root",
            parentId = null,
            deleted = false
        )

        val branch1Nodes = mutableListOf<RemoteNodeEntity>().apply {
            add(RemoteNodeEntity("node1", "Node 1", "root", false))
            add(RemoteNodeEntity("node2", "Node 2", "node1", false))
            add(RemoteNodeEntity("node3", "Node 3", "node2", false))
            add(RemoteNodeEntity("node4", "Node 4", "node3", false))
            add(RemoteNodeEntity("node5", "Node 5", "node4", false))
        }

        val branch2Nodes = mutableListOf<RemoteNodeEntity>().apply {
            add(RemoteNodeEntity("node6", "Node 6", "root", false))
            add(RemoteNodeEntity("node7", "Node 7", "node6", false))
            add(RemoteNodeEntity("node8", "Node 8", "node7", false))
            add(RemoteNodeEntity("node9", "Node 9", "node8", false))
            add(RemoteNodeEntity("node10", "Node 10", "node9", false))
        }

        // Вставляем все узлы
        insertNode(rootNode)
        branch1Nodes.forEach { insertNode(it) }
        branch2Nodes.forEach { insertNode(it) }
    }
}