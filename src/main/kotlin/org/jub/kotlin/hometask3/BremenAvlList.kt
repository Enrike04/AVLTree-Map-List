package org.jub.kotlin.hometask3

class BremenAvlList<K : Comparable<K>, V> : BremenAvl<K, V>(), MutableAvlTreeList<K, V> {
    override fun contains(element: V): Boolean = containsValue(element)

    override fun containsAll(elements: Collection<V>): Boolean = elements.all { contains(it) }

    override fun add(element: V): Boolean {
        TODO("Not yet implemented")
    }

    override fun add(index: Int, element: V) {
        TODO("Not yet implemented")
    }

    override fun addAll(index: Int, elements: Collection<V>): Boolean {
        TODO("Not yet implemented")
    }

    override fun addAll(elements: Collection<V>): Boolean {
        TODO("Not yet implemented")
    }

    override fun get(index: Int): V {
        TODO("Not yet implemented")
    }

    override fun iterator(): MutableIterator<V> {
        TODO("Not yet implemented")
    }

    override fun listIterator(): MutableListIterator<V> {
        TODO("Not yet implemented")
    }

    override fun listIterator(index: Int): MutableListIterator<V> {
        TODO("Not yet implemented")
    }

    override fun removeAt(index: Int): V {
        TODO("Not yet implemented")
    }

    override fun subList(fromIndex: Int, toIndex: Int): MutableList<V> {
        TODO("Not yet implemented")
    }

    override fun set(index: Int, element: V): V {
        TODO("Not yet implemented")
    }

    override fun retainAll(elements: Collection<V>): Boolean {
        TODO("Not yet implemented")
    }

    override fun removeAll(elements: Collection<V>): Boolean {
        TODO("Not yet implemented")
    }

    override fun remove(element: V): Boolean {
        TODO("Not yet implemented")
    }

    override fun lastIndexOf(element: V): Int {
        TODO("Not yet implemented")
    }

    override fun indexOf(element: V): Int {
        TODO("Not yet implemented")
    }
}