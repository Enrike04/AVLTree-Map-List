package org.jub.kotlin.hometask3

class AvlTreeImpl<K : Comparable<K>, V>(from: Collection<Pair<K, V>> = emptyList()) :
    AvlTreeList<K, V>, MutableAvlTreeMap<K, V> {
    override val height: Int
        get() = TODO("Not yet implemented")
    override val entries: MutableSet<MutableMap.MutableEntry<K, V>>
        get() = TODO("Not yet implemented")
    override val keys: MutableSet<K>
        get() = TODO("Not yet implemented")

    override val size: Int
        get() = TODO("Not yet implemented")
    override val values: MutableCollection<V>
        get() = TODO("Not yet implemented")

    init {
        println("What a collection! $from")
    }

    override fun maximumKey(): K = TODO("Not yet implemented")

    override fun minimumKey(): K = TODO("Not yet implemented")

    override fun maximumValue(): V = TODO("Not yet implemented")

    override fun minimumValue(): V = TODO("Not yet implemented")

    override fun clear() {
        TODO("Not yet implemented")
    }

    override fun get(key: K): V? {
        TODO("Not yet implemented")
    }

    override fun containsValue(value: V): Boolean {
        TODO("Not yet implemented")
    }

    override fun containsKey(key: K): Boolean {
        TODO("Not yet implemented")
    }

    override fun get(index: Int): V {
        TODO("Not yet implemented")
    }

    override fun isEmpty(): Boolean {
        TODO("Not yet implemented")
    }

    override fun remove(key: K): V? {
        TODO("Not yet implemented")
    }

    override fun putAll(from: Map<out K, V>) {
        TODO("Not yet implemented")
    }

    override fun put(key: K, value: V): V? {
        TODO("Not yet implemented")
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

    override fun containsAll(elements: Collection<V>): Boolean {
        TODO("Not yet implemented")
    }

    override fun contains(element: V): Boolean {
        TODO("Not yet implemented")
    }

    override fun merge(other: MutableAvlTreeMap<K, V>): MutableAvlTreeMap<K, V> {
        TODO("Not yet implemented")
    }
}
