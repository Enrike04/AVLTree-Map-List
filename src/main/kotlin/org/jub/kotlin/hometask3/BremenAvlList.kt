package org.jub.kotlin.hometask3

import java.util.*

class BremenAvlList<K : Comparable<K>, V>() : BremenAvlBase<K, V>(), AvlTreeList<K, V> {
    constructor(from: Collection<Pair<K, V>>) : this() {
        from.forEach { (key, value) ->
            put(key, value)
        }
    }

    override fun contains(element: V): Boolean = containsValue(element)

    override fun containsAll(elements: Collection<V>): Boolean = elements.all { contains(it) }

    override fun get(index: Int): V {
        if (root == null || index >= root?.size!!) {
            throw IndexOutOfBoundsException("Index $index is out of bounds")
        }
        var mutableIndex = index
        var curr = root
        while (true) {
            val leftSize = curr?.left?.size ?: 0
            curr = when {
                mutableIndex < leftSize -> curr?.left
                mutableIndex == leftSize -> return curr!!.value
                else -> {
                    mutableIndex -= leftSize
                    curr?.right
                }
            }
        }
    }

    override fun iterator(): Iterator<V> = listIterator()

    override fun listIterator(): ListIterator<V> = listIterator(0)

    override fun listIterator(index: Int): ListIterator<V> = object : ListIterator<V> {
        private val pathFromRoot: Stack<BremenAvlNode<K, V>> = Stack<BremenAvlNode<K, V>>()
        private var currIndex = 0

        init {
            root?.let { pathFromRoot.push(it) }
        }

        override fun hasNext(): Boolean {
            TODO("Not yet implemented")
        }

        override fun hasPrevious(): Boolean {
            TODO("Not yet implemented")
        }

        override fun next(): V {
            TODO("Not yet implemented")
        }

        override fun nextIndex(): Int {
            TODO("Not yet implemented")
        }

        override fun previous(): V {
            TODO("Not yet implemented")
        }

        override fun previousIndex(): Int {
            TODO("Not yet implemented")
        }
    }

    override fun subList(fromIndex: Int, toIndex: Int): List<V> {
        TODO("Not yet implemented")
    }

    override fun lastIndexOf(element: V): Int {
        TODO("Not yet implemented")
    }

    override fun indexOf(element: V): Int {
        TODO("Not yet implemented")
    }
}