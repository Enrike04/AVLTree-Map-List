package org.jub.kotlin.hometask3

import org.junit.jupiter.api.RepeatedTest
import kotlin.test.*

internal class AvlTreeMapTest {
    @Test
    fun getSizeEmpty() {
        val emptyAvl: AvlTreeMap<Int, String> = AvlTreeImpl()
        assertEquals(0, emptyAvl.size, "Default size should be 0")
    }

    @RepeatedTest(testIterations)
    fun getSize() {
        val values = getSetOfRandomValues()
        val avl: AvlTreeMap<Int, String> = AvlTreeImpl(values.zip(values.map { it.toString() }))
        assertEquals(values.size, avl.size, "Values and avl size should be equal")
    }

    @Test
    fun isEmpty() {
        val emptyAvl: AvlTreeMap<Int, String> = AvlTreeImpl()
        assertTrue(emptyAvl.isEmpty(), "Default tree should be empty")
    }

    @RepeatedTest(testIterations)
    fun getEntries() {
        val values = getSetOfRandomValues()
        val doubleValues = values.map { it.toDouble() }.toSet()
        val avl: AvlTreeMap<Int, Double> = AvlTreeImpl(values.zip(doubleValues))
        val entries = avl.entries
        assertTrue(entries.all { it.key in values && it.value in doubleValues })
        assertTrue(values.all { value -> value in entries.map { it.key } })
        assertTrue(doubleValues.all { doubleValue -> doubleValue in entries.map { it.value } })
    }

    @RepeatedTest(testIterations)
    fun containsKey() {
        val values = getSetOfRandomValues()
        val avl: AvlTreeMap<Int, Double> = AvlTreeImpl(values.zip(values.map { it.toDouble() }))
        assertTrue(values.all { avl.containsKey(it) })
        val otherValues = getSetOfRandomValues()
        otherValues.filter { it !in values }.forEach {
            assertFalse(avl.containsKey(it))
        }
    }

    @RepeatedTest(testIterations)
    fun containsValue() {
        val values = getSetOfRandomValues()
        val avl: AvlTreeMap<Int, Double> = AvlTreeImpl(values.zip(values.map { it.toDouble() }))
        assertTrue(values.all { avl.containsValue(it.toDouble()) })
        val otherValues = getSetOfRandomValues()
        otherValues.filter { it !in values }.forEach {
            assertFalse(avl.containsValue(it.toDouble()))
        }
    }

    @RepeatedTest(testIterations)
    fun get() {
        val values = getSetOfRandomValues()
        val avl: AvlTreeMap<Int, Double> = AvlTreeImpl(values.zip(values.map { it.toDouble() }))
        values.forEach {
            assertEquals(it.toDouble(), avl[it])
        }
        val otherValues = getSetOfRandomValues()
        otherValues.filter { it !in values }.forEach {
            assertNull(avl[it])
        }
    }
}