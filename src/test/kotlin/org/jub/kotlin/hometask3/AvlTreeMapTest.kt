package org.jub.kotlin.hometask3

import kotlin.random.Random
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class AvlTreeMapTest {
    private val testIterations = 10
    private val testSetSize = 16
    private fun getSetOfRandomValues() = IntArray(testSetSize) { Random.nextInt() }.toSet()

    @Test
    fun getSize() {
        val emptyAvl: AvlTreeMap<Int, String> = BremenAvl()
        assertEquals(0, emptyAvl.size, "Default size should be 0")

        for (i in 1..testIterations) {
            val values = getSetOfRandomValues()
            val avl: AvlTreeMap<Int, String> = BremenAvl(values.zip(values.map { it.toString() }))
            assertEquals(values.size, avl.size, "Values and avl size should be equal")
        }
    }

    @Test
    fun isEmpty() {
        val emptyAvl: AvlTreeMap<Int, String> = BremenAvl()
        assertTrue(emptyAvl.isEmpty(), "Default tree should be empty")
    }

    @Test
    fun maximumKey() {
        for (i in 1..testIterations) {
            val values = getSetOfRandomValues()
            val avl: AvlTreeMap<Int, Double> = BremenAvl(values.zip(values.map { it.toDouble() }))
            assertEquals(values.max(), avl.maximumKey(), "Wrong max key")
        }
    }

    @Test
    fun minimumKey() {
        for (i in 1..testIterations) {
            val values = getSetOfRandomValues()
            val avl: AvlTreeMap<Int, Double> = BremenAvl(values.zip(values.map { it.toDouble() }))
            assertEquals(values.min(), avl.minimumKey(), "Wrong min key")
        }
    }

    @Test
    fun maximumValue() {
        for (i in 1..testIterations) {
            val values = getSetOfRandomValues()
            val avl: AvlTreeMap<Int, Double> = BremenAvl(values.zip(values.map { it.toDouble() }))
            assertEquals(values.max().toDouble(), avl.maximumValue(), "Wrong max value")
        }
    }

    @Test
    fun minimumValue() {
        for (i in 1..testIterations) {
            val values = getSetOfRandomValues()
            val avl: AvlTreeMap<Int, Double> = BremenAvl(values.zip(values.map { it.toDouble() }))
            assertEquals(values.min().toDouble(), avl.minimumValue(), "Wrong min value")
        }
    }

    @Test
    fun getEntries() {
        for (i in 1..testIterations) {
            val values = getSetOfRandomValues()
            val doubleValues = values.map { it.toDouble() }.toSet()
            val avl: AvlTreeMap<Int, Double> = BremenAvl(values.zip(doubleValues))
            val entries = avl.entries
            assertTrue(entries.all { it.key in values && it.value in doubleValues})
            assertTrue(values.all { it in entries.map { it.key }})
            assertTrue(doubleValues.all { it in entries.map { it.value }})
        }
    }

    @Test
    fun containsKey() {
        for (i in 1..testIterations) {
            val values = getSetOfRandomValues()
            val avl: AvlTreeMap<Int, Double> = BremenAvl(values.zip(values.map { it.toDouble() }))
            assertTrue(values.all { avl.containsKey(it) })
            for (j in 1..testSetSize) {
                val testVal = Random.nextInt()
                assertTrue(testVal in values || !avl.containsKey(testVal))
            }
        }
    }

    @Test
    fun containsValue() {
        for (i in 1..testIterations) {
            val values = getSetOfRandomValues()
            val avl: AvlTreeMap<Int, Double> = BremenAvl(values.zip(values.map { it.toDouble() }))
            assertTrue(values.all { avl.containsValue(it.toDouble()) })
            for (j in 1..testSetSize) {
                val testVal = Random.nextInt()
                assertTrue(testVal in values || !avl.containsValue(testVal.toDouble()))
            }
        }
    }

    @Test
    fun get() {
        for (i in 1..testIterations) {
            val values = getSetOfRandomValues()
            val avl: AvlTreeMap<Int, Double> = BremenAvl(values.zip(values.map { it.toDouble() }))
            values.forEach {
                assertEquals(it.toDouble(), avl[it])
            }
            for (j in 1..testSetSize) {
                val testVal = Random.nextInt()
                assertTrue(testVal in values || avl[testVal] == null)
            }
        }
    }
}