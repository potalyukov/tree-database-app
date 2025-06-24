package com.example.treedatabase.domain.mappers

import com.example.treedatabase.domain.models.NodeDomain
import javax.inject.Inject

class TreeMapper @Inject constructor() {
    fun sortAsTreeInDepth(list: List<NodeDomain>): List<NodeDomain> {
        val nodeMap = list.associateBy { it.id }
        val childrenMap = list.groupBy { it.parent }

        val roots = list.filter { it.parent == null || it.parent !in nodeMap }

        val result = mutableListOf<NodeDomain>()
        val stack = ArrayDeque<Pair<NodeDomain, Int>>() // Pair of node and depth

        for (root in roots.sortedByDescending { it.value }) {
            stack.addFirst(root to 0)
        }

        while (stack.isNotEmpty()) {
            val (node, depth) = stack.removeFirst()
            val updatedNode = node.copy(depth = depth)
            result.add(updatedNode)

            val children = childrenMap[node.id].orEmpty().sortedByDescending { it.value }
            for (child in children) {
                stack.addFirst(child to depth + 1)
            }
        }

        return result
    }
}