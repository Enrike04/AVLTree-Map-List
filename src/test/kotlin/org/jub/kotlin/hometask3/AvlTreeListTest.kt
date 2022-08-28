package org.jub.kotlin.hometask3

import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class AvlTreeListTest {

    @Test
    fun contains() {
        val values = getSetOfRandomValues()
        val avl: AvlTreeList<Int, Double> = AvlTreeImpl(values.zip(values.map { it.toDouble() }))
        values.forEach {
            assertTrue(avl.contains(it.toDouble()))
        }
        val otherValues = getSetOfRandomValues()
        otherValues.filter { it !in values }.forEach {
            assertFalse(avl.contains(it.toDouble()))
        }
    }

    @Test
    fun containsAll() {
    }

    @Test
    fun get() {
    }

    @Test
    operator fun iterator() {
    }

    @Test
    fun listIterator() {
    }

    @Test
    fun testListIterator() {
    }

    @Test
    fun subList() {
    }

    @Test
    fun lastIndexOf() {
    }

    @Test
    fun indexOf() {
    }
}