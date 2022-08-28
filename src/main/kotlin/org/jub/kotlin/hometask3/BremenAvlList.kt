package org.jub.kotlin.hometask3

import kotlin.ConcurrentModificationException

class BremenAvlList<K : Comparable<K>, V>() : BremenAvlBase<K, V>(), AvlTreeList<K, V> {
    constructor(from: Collection<Pair<K, V>>) : this() {
        from.forEach { (key, value) ->
            put(key, value)
        }
    }

    fun isNotEmpty() = !isEmpty()

    override fun contains(element: V): Boolean = containsValue(element)

    override fun containsAll(elements: Collection<V>): Boolean = elements.all { contains(it) }

    override fun get(index: Int): V {
        require(index >= 0) { "Index must be positive" }
        if (root == null || index >= root?.size!!) {
            throw IndexOutOfBoundsException("Index $index is out of bounds")
        }
        var mutableIndex = index
        var curr = root
        while (mutableIndex != 0) {
            val leftSize = curr?.left?.size ?: 0
            curr = when {
                mutableIndex < leftSize -> curr?.left
                mutableIndex == leftSize -> return curr!!.value
                else -> {
                    mutableIndex -= leftSize + 1
                    curr?.right
                }
            }
        }
        return curr!!.leftmost().value
    }

    override fun iterator(): Iterator<V> = listIterator()

    override fun listIterator(): ListIterator<V> = listIterator(0)

    override fun listIterator(index: Int): ListIterator<V> = object : ListIterator<V> {
        private var cursor = 0
        private val modCount = this@BremenAvlList.modCount
        private val source = this@BremenAvlList

        init {
            if (index >= source.size || index < 0) {
                throw IndexOutOfBoundsException("Index: $index, Size: ${source.size}")
            }
        }

        private fun checkForComodification() {
            if (this.modCount != source.modCount)
                throw ConcurrentModificationException("Iterator is invalid since the original list was modified")
        }

        override fun hasNext(): Boolean {
            checkForComodification()
            return isNotEmpty() && cursor != source.size
        }

        override fun hasPrevious(): Boolean {
            checkForComodification()
            return source.isNotEmpty() && cursor > 0
        }

        override fun next(): V {
            checkForComodification()
            if (!hasNext())
                throw IndexOutOfBoundsException("Index: $cursor, Size: ${source.size}")
            val value = source[cursor]
            cursor += 1
            return value
        }

        override fun nextIndex(): Int {
            return cursor
        }

        override fun previous(): V {
            checkForComodification()
            if (!hasPrevious())
                throw throw IndexOutOfBoundsException("Index: $cursor, Size: ${source.size}")
            cursor -= 1
            return source[cursor]
        }

        override fun previousIndex(): Int {
            return cursor - 1
        }
    }

    override fun subList(fromIndex: Int, toIndex: Int): List<V> {
        TODO("Not yet implemented")
    }

    override fun lastIndexOf(element: V): Int {
        var index = -1
        val iterator = listIterator()
        while (iterator.hasNext()) {
            if (iterator.next() == element) {
                index = iterator.previousIndex()
            }
        }
        return index
    }

    override fun indexOf(element: V): Int {
        val iterator = listIterator()
        while (iterator.hasNext()) {
            if (iterator.next() == element) {
                return iterator.previousIndex()
            }
        }
        return -1
    }

    // modifications
    internal var modCount = 0

    override fun clear() {
        modCount += 1
        super.clear()
    }

    override fun put(key: K, value: V): V? {
        modCount += 1
        return super.put(key, value)
    }

    override fun remove(key: K): V? {
        modCount += 1
        return super.remove(key)
    }

    // Too lazy to do putIfAbsent and so on, hope they use methods above
}