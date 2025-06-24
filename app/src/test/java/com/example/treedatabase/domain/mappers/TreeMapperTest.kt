package com.example.treedatabase.domain.mappers

import com.example.treedatabase.domain.models.NodeDomain
import org.junit.Assert.assertEquals
import org.junit.Test

const val BIG_DEPTH = 100000

class TreeMapperTest {

    private val treeMapper = TreeMapper()

    @Test
    fun `sortAsTreeInDepth should sort big tree`() {
        val ids = List(BIG_DEPTH) { i -> "node${i + 1}" }
        val nodes: MutableList<NodeDomain> = mutableListOf()

        ids.forEachIndexed { index, id ->
            val newNode = NodeDomain(
                id = id,
                value = id,
                parent = if (index == 0) null else ids[index - 1],
                deleted = false
            )
            nodes.add(newNode)
        }

        val result = treeMapper.sortAsTreeInDepth(nodes)

        result.forEachIndexed { index, nodeDomain ->
            assertEquals(index, nodeDomain.depth)
        }
    }

    @Test
    fun `sortAsTreeInDepth should assign correct depth and order`() {
        val idA = "node1"
        val idB = "node2"
        val idC = "node3"
        val idD = "node4"
        val idE = "node5"

        val input = listOf(
            NodeDomain(id = idA, value = "A", parent = null, deleted = false),
            NodeDomain(id = idB, value = "B", parent = idA, deleted = false),
            NodeDomain(id = idC, value = "C", parent = idA, deleted = false),
            NodeDomain(id = idD, value = "D", parent = idB, deleted = false),
            NodeDomain(id = idE, value = "E", parent = null, deleted = false)
        )

        val result = treeMapper.sortAsTreeInDepth(input)

        val expected = listOf(
            NodeDomain(id = idA, value = "A", parent = null, deleted = false, depth = 0),
            NodeDomain(id = idB, value = "B", parent = idA, deleted = false, depth = 1),
            NodeDomain(id = idD, value = "D", parent = idB, deleted = false, depth = 2),
            NodeDomain(id = idC, value = "C", parent = idA, deleted = false, depth = 1),
            NodeDomain(id = idE, value = "E", parent = null, deleted = false, depth = 0)
        )

        assertEquals(expected, result)
    }
}