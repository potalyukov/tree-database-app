package com.example.treedatabase.domain.mappers

import com.example.treedatabase.domain.models.NodeDomain
import javax.inject.Inject

class TreeMapper @Inject constructor() {
    fun sortAsTreeInDepth(list: List<NodeDomain>): List<NodeDomain> {
        val treeMap = list.groupBy { it.parent }

        fun traverse(parentId: Long?, depth: Int): List<NodeDomain> {
            val children = treeMap[parentId].orEmpty()
            return children.flatMap { node ->
                val updatedNode = node.copy(depth = depth)
                listOf(updatedNode) + traverse(node.id, depth + 1)
            }
        }

        return traverse(parentId = null, depth = 0)
    }
}