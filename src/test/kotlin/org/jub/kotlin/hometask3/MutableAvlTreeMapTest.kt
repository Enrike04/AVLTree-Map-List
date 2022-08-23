package org.jub.kotlin.hometask3

import org.junit.jupiter.api.RepeatedTest
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

class MutableAvlTreeMapTest {

    @RepeatedTest(testIterations)
    fun clear() {
        val values = getSetOfRandomValues()
        val avl: MutableAvlTreeMap<Int, String> = AvlTreeMapImpl(values.zip(values.map { it.toString() }))
        avl.clear()
        assertTrue(avl.isEmpty())
        assertTrue(avl.size == 0)
    }

    @RepeatedTest(testIterations)
    fun remove() {
        val values = getSetOfRandomValues()
        val avl: MutableAvlTreeMap<Int, String> = AvlTreeMapImpl(values.zip(values.map { it.toString() }))
        var expectedSize = values.size
        values.forEach {
            avl.remove(it)
            expectedSize -= 1
            assertEquals(expectedSize, avl.size)
        }
        assertTrue(avl.isEmpty())
    }

    @RepeatedTest(testIterations)
    fun put() {
        val values = getSetOfRandomValues()
        val avl: MutableAvlTreeMap<Int, String> = AvlTreeMapImpl()
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