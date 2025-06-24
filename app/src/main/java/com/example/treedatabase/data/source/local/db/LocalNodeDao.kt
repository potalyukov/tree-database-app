package com.example.treedatabase.data.source.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface LocalNodeDao {
    @Query("SELECT * FROM nodes")
    fun getAllNodes(): Flow<List<LocalNodeEntity>>

    @Query("SELECT * FROM nodes")
    suspend fun getAllSuspend(): List<LocalNodeEntity>

    @Query("SELECT * FROM nodes WHERE uid = :uid")
    suspend fun getNodeById(uid: String): LocalNodeEntity?

    @Query(
        """
        WITH RECURSIVE node_tree AS (
            SELECT uid FROM nodes WHERE uid = :uid
            UNION ALL
            SELECT n.uid FROM nodes n
            JOIN node_tree nt ON n.parentId = nt.uid
        )
        UPDATE nodes SET deleted = 1 WHERE uid IN node_tree
    """
    )
    suspend fun deleteNodeRecursively(uid: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun applyNodes(nodes: List<LocalNodeEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun applyNode(node: LocalNodeEntity)

    @Transaction
    suspend fun createNode(node: LocalNodeEntity) {
        if (node.parentId == null) return
        val parent = getNodeById(node.parentId)
        if (parent == null || parent.deleted) return
        applyNode(node)
    }

    @Query("DELETE FROM nodes")
    suspend fun clearAllNodes()

    @Query("UPDATE nodes SET deleted = 0 WHERE uid = :id")
    suspend fun markNodeUndeleted(id: String)

    @Transaction
    suspend fun applyNodeIfNotDeleted(node: LocalNodeEntity) {
        val existing = getNodeById(node.uid)
        if (existing != null && existing.deleted) {
            return
        }
        applyNodes(listOf(node))
    }

    @Transaction
    suspend fun applyNodeWithUndeleteChain(node: LocalNodeEntity) {
        if (!node.deleted) {
            var currentParentId = node.parentId
            while (currentParentId != null) {
                val parent = getNodeById(currentParentId)
                if (parent == null || !parent.deleted) break

                markNodeUndeleted(parent.uid)
                currentParentId = parent.parentId
            }
        }

        applyNodes(listOf(node))
    }
}