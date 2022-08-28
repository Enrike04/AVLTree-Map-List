package org.jub.kotlin.hometask3

import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test
import kotlin.math.floor
import kotlin.math.log
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class AvlTreeTest {

    @RepeatedTest(testIterations)
    fun maximumKey() {
        val values = getSetOfRandomValues()
        val avl: AvlTreeMap<Int, Double> = AvlTreeImpl(values.zip(values.map { it.toDouble() }))
        assertEquals(values.max(), avl.maximumKey(), "Wrong max key")
    }

    @RepeatedTest(testIterations)
    fun minimumKey() {
        val values = getSetOfRandomValues()
        val avl: AvlTreeMap<Int, Double> = AvlTreeImpl(values.zip(values.map { it.toDouble() }))
        assertEquals(values.min(), avl.minimumKey(), "Wrong min key")
    }

    @RepeatedTest(testIterations)
    fun maximumValue() {
        val values = getSetOfRandomValues()
        val avl: AvlTreeMap<Int, Double> = AvlTreeImpl(values.zip(values.map { it.toDouble() }))
        assertEquals(values.max().toDouble(), avl.maximumValue(), "Wrong max value")
    }

    @RepeatedTest(testIterations)
    fun minimumValue() {
        val values = getSetOfRandomValues()
        val avl: AvlTreeMap<Int, Double> = AvlTreeImpl(values.zip(values.map { it.toDouble() }))
        assertEquals(values.min().toDouble(), avl.minimumValue(), "Wrong min value")
    }

    @RepeatedTest(testIterations)
    fun minmaxThrows() {
        val avl: AvlTreeMap<Int, Double> = AvlTreeImpl()
        assertThrows<Exception>("Empty tree should throw") {
            avl.minimumValue()
        }
        assertThrows<Exception>("Empty tree should throw") {
            avl.maximumValue()
        }
        assertThrows<Exception>("Empty tree should throw") {
            avl.minimumKey()
        }
        assertThrows<Exception>("Empty tree should throw") {
            avl.maximumKey()
        }
    }

    private fun log2(size: Int) = log(size.toDouble(), 2.0)

    @Test
    fun getHeight() {
        for (size in 50..2050 step 100) {
            val values = getSetOfRandomValues(size)
            val avl: AvlTreeMap<Int, Double> = AvlTreeImpl(values.zip(values.map { it.toDouble() }))
            assertTrue(avl.height > floor(log2(size)))
            assertTrue(avl.height < 1.44 * log2(size))
        }
    }
}