package org.jub.kotlin.hometask3

import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class AvlTreeMapTest {
    private fun getSetOfRandomValues(size: Int) =  IntArray(size) { Random.nextInt() }.toSet()

    @Test
    fun getSize() {
        val emptyAvl: AvlTreeMap<Int, String> = BremenAvl()
        assertEquals(0, emptyAvl.size, "Default size should be 0")

        for (i in 1..10) {
            val values = getSetOfRandomValues(i)
            val avl: AvlTreeMap<Int, String> = BremenAvl(values.zip(values.map { it.toString() }))
            assertEquals(values.size, avl.size, "Values and avl size should be equal")
        }
    }

    @Test
    fun isEmpty() {
        val emptyAvl: AvlTreeMap<Int, String> = BremenAvl()
        assertTrue(emptyAvl.isEmpty(), "Default tree should be empty")
    }
}