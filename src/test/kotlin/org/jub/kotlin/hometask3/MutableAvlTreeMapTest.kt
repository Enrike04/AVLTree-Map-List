package org.jub.kotlin.hometask3

import org.junit.jupiter.api.RepeatedTest
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class MutableAvlTreeMapTest {
    @RepeatedTest(TEST_ITERATIONS)
    fun clear() {
        val values = getSetOfRandomValues()
        val avl: MutableAvlTreeMap<Int, String> = AvlTreeImpl(values.zip(values.map { it.toString() }))
        avl.clear()
        assertTrue(avl.isEmpty())
        assertTrue(avl.size == 0)
    }

    @RepeatedTest(TEST_ITERATIONS)
    fun remove() {
        val values = getSetOfRandomValues()
        val avl: MutableAvlTreeMap<Int, String> = AvlTreeImpl(values.zip(values.map { it.toString() }))
        var expectedSize = values.size
        values.forEach {
            avl.remove(it)
            expectedSize -= 1
            assertEquals(expectedSize, avl.size)
        }
        assertTrue(avl.isEmpty())
    }

    @RepeatedTest(TEST_ITERATIONS)
    fun put() {
        val values = getSetOfRandomValues()
        val avl: MutableAvlTreeMap<Int, String> = AvlTreeImpl(emptyList())
        assertTrue(avl.isEmpty())
        var expectedSize = 0
        values.forEach {
            val old = avl.put(it, it.toString())
            expectedSize += 1
            assertEquals(expectedSize, avl.size)
            assertNull(old)
        }
        values.forEach {
            val old = avl.put(it, it.toString())
            expectedSize += 1
            assertEquals(it.toString(), old)
        }
    }
}
