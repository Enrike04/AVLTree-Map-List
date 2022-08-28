package org.jub.kotlin.hometask3

import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.assertThrows
import kotlin.math.floor
import kotlin.math.log
import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class AvlTreeMapTest {
    @Test
    fun getSizeEmpty() {
        val emptyAvl: AvlTreeMap<Int, String> = AvlTreeMapImpl()
        assertEquals(0, emptyAvl.size, "Default size should be 0")
    }

    @RepeatedTest(testIterations)
    fun getSize() {
        val values = getSetOfRandomValues()
        val avl: AvlTreeMap<Int, String> = AvlTreeMapImpl(values.zip(values.map { it.toString() }))
        assertEquals(values.size, avl.size, "Values and avl size should be equal")
    }

    @Test
    fun isEmpty() {
        val emptyAvl: AvlTreeMap<Int, String> = AvlTreeMapImpl()
        assertTrue(emptyAvl.isEmpty(), "Default tree should be empty")
    }

    @RepeatedTest(testIterations)
    fun getEntries() {
        val values = getSetOfRandomValues()
        val doubleValues = values.map { it.toDouble() }.toSet()
        val avl: AvlTreeMap<Int, Double> = AvlTreeMapImpl(values.zip(doubleValues))
        val entries = avl.entries
        assertTrue(entries.all { it.key in values && it.value in doubleValues })
        assertTrue(values.all { value -> value in entries.map { it.key } })
        assertTrue(doubleValues.all { doubleValue -> doubleValue in entries.map { it.value } })
    }

    @RepeatedTest(testIterations)
    fun containsKey() {
        val values = getSetOfRandomValues()
        val avl: AvlTreeMap<Int, Double> = AvlTreeMapImpl(values.zip(values.map { it.toDouble() }))
        assertTrue(values.all { avl.containsKey(it) })
        for (j in 1..testSetSize) {
            val testVal = Random.nextInt()
            assertTrue(testVal in values || !avl.containsKey(testVal))
        }
    }

    @RepeatedTest(testIterations)
    fun containsValue() {
        val values = getSetOfRandomValues()
        val avl: AvlTreeMap<Int, Double> = AvlTreeMapImpl(values.zip(values.map { it.toDouble() }))
        assertTrue(values.all { avl.containsValue(it.toDouble()) })
        for (j in 1..testSetSize) {
            val testVal = Random.nextInt()
            assertTrue(testVal in values || !avl.containsValue(testVal.toDouble()))
        }
    }

    @RepeatedTest(testIterations)
    fun get() {
        val values = getSetOfRandomValues()
        val avl: AvlTreeMap<Int, Double> = AvlTreeMapImpl(values.zip(values.map { it.toDouble() }))
        values.forEach {
            assertEquals(it.toDouble(), avl[it])
        }
        for (j in 1..testSetSize) {
            val testVal = Random.nextInt()
            assertTrue(testVal in values || avl[testVal] == null)
        }
    }
}