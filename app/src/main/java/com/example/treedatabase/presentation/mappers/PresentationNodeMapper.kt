package com.example.treedatabase.presentation.mappers

import com.example.treedatabase.domain.models.NodeDomain
import com.example.treedatabase.presentation.model.NodeUi
import javax.inject.Inject

class PresentationNodeMapper @Inject constructor() {

    fun toDomain(nodeUi:  NodeUi): NodeDomain {
        return NodeDomain(
            id = nodeUi.id,
            value = nodeUi.value,
            parent = nodeUi.parentId,
            deleted = nodeUi.deleted
        )
    }

    fun toUi(domain: NodeDomain): NodeUi {
        return NodeUi(
            id = domain.id,
            value = domain.value,
            parentId = domain.parent,
            deleted = domain.deleted
        )
    }

}