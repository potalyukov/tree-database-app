package com.example.treedatabase.data.mappers

import com.example.treedatabase.data.model.NodeData
import com.example.treedatabase.data.source.local.db.LocalNodeEntity
import com.example.treedatabase.data.source.remote.api.simulated_remote_db.RemoteNodeEntity
import com.example.treedatabase.domain.models.NodeDomain
import javax.inject.Inject

class NodeMapper @Inject constructor() {

    fun toDomain(data: NodeData): NodeDomain {
        return NodeDomain(
            id = data.id,
            value = data.value,
            parent = data.parentId,
            deleted = data.deleted
        )
    }

    fun toData(domain: NodeDomain): NodeData {
        return NodeData(
            id = domain.id,
            value = domain.value,
            parentId = domain.parent,
            deleted = domain.deleted
        )
    }

    fun toData(dbEntity: RemoteNodeEntity): NodeData {
        return NodeData(
            id = dbEntity.uid,
            value = dbEntity.value,
            parentId = dbEntity.parentId,
            deleted = dbEntity.deleted
        )
    }

    fun toData(dbEntity: LocalNodeEntity): NodeData {
        return NodeData(
            id = dbEntity.uid,
            value = dbEntity.value,
            parentId = dbEntity.parentId,
            deleted = dbEntity.deleted
        )
    }

    fun toRemoteDbEntity(data: NodeData): RemoteNodeEntity {
        return RemoteNodeEntity(
            uid = data.id,
            value = data.value,
            parentId = data.parentId,
            deleted = data.deleted
        )
    }

    fun toLocalDbEntity(data: NodeData): LocalNodeEntity {
        return LocalNodeEntity(
            uid = data.id,
            value = data.value,
            parentId = data.parentId,
            deleted = data.deleted
        )
    }
}