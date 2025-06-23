package com.example.treedatabase.domain.mappers

import com.example.treedatabase.domain.models.NodeDomain
import javax.inject.Inject

class TreeMapper @Inject constructor() {
    fun sortAsTreeInDepth(list: List<NodeDomain>): List<NodeDomain> {
        val nodeMap = list.associateBy { it.id }
        val childrenMap = list.groupBy { it.parent }

        val roots = list.filter { it.parent == null || it.parent !in nodeMap }

        val result = mutableListOf<NodeDomain>()

        fun dfs(node: NodeDomain, depth: Int) {
            val updated = node.copy(depth = depth)
            result.add(updated)
            val children = childrenMap[node.id].orEmpty()
            for (child in children) {
                dfs(child, depth + 1)
            }
        }

        for (root in roots) {
            dfs(root, 0)
        }

        return result
    }
}