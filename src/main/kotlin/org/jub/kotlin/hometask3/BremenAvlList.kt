package org.jub.kotlin.hometask3

import kotlin.ConcurrentModificationException

class BremenAvlList<K : Comparable<K>, V>() : BremenAvlBase<K, V>(), AvlTreeList<K, V> {
    constructor(from: Collection<Pair<K, V>>) : this() {
        from.forEach { (key, value) ->
            put(key, value)
        }
    }

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

        private fun checkForComodification() {
            if (this.modCount != source.modCount)
                throw ConcurrentModificationException("Iterator is invalid since the original list was modified")
        }

        override fun hasNext(): Boolean {
            checkForComodification()
            return source.size > 0 && cursor != source.size - 1
        }

        override fun hasPrevious(): Boolean {
            return cursor > 0
        }

        override fun next(): V {
            if (!hasNext())
                throw ArrayIndexOutOfBoundsException("Cannot get index $cursor from the list")
            val value = source[cursor]
            cursor += 1
            return value
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

    override fun putAll(from: Map<out K, V>) {
        modCount += 1
        super.putAll(from)
    }

    override fun remove(key: K): V? {
        modCount += 1
        return super.remove(key)
    }

    // Too lazy to do putIfAbsent and so on
}