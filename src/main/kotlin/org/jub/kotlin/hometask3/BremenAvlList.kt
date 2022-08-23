package org.jub.kotlin.hometask3

class BremenAvlList<K : Comparable<K>, V> : BremenAvlBase<K, V>(), AvlTreeList<K, V> {
    override fun contains(element: V): Boolean = containsValue(element)

    override fun containsAll(elements: Collection<V>): Boolean = elements.all { contains(it) }

    override fun get(index: Int): V {
        if (root == null || index >= root?.size!!) {
            throw IndexOutOfBoundsException("Index $index is out of bounds")
        }
        var mutableIndex = index
        var curr = root
        while(true) {
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

    override fun iterator(): Iterator<V> {
        TODO("Not yet implemented")
    }

    override fun listIterator(): ListIterator<V> {
        TODO("Not yet implemented")
    }

    override fun listIterator(index: Int): ListIterator<V> {
        TODO("Not yet implemented")
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