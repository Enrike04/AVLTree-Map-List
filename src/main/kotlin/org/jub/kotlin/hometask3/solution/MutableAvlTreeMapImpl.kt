package org.jub.kotlin.hometask3.solution

import org.jub.kotlin.hometask3.MutableAvlTreeMap

sealed class MutableAvlTreeMapImpl<K : Comparable<K>, V> : BaseAvlTreeImpl<K, V>(), MutableAvlTreeMap<K, V> {
    override fun merge(other: MutableAvlTreeMap<out K, out V>): MutableAvlTreeMap<K, V> {
        if (other.minimumKey() <= maximumKey()) {
            throw IllegalArgumentException("Illegal Merge")
        }
        other.forEach { (k, v) -> put(k, v) }
        return this
    }

    override val entries: MutableSet<MutableMap.MutableEntry<K, V>>
        get() = TODO("Not yet implemented")
    override val keys: MutableSet<K>
        get() = TODO("Not yet implemented")
    override val values: MutableCollection<V>
        get() = TODO("Not yet implemented")

    override fun clear() {
        root = null
        size = 0
    }

    override fun isEmpty() = root == null

    override fun remove(key: K): V? {
        TODO("Not yet implemented")
    }

    override fun putAll(from: Map<out K, V>) {
        from.forEach { (k, v) -> put(k, v) }
    }

    override fun put(key: K, value: V): V? = insert(key, value)

    private fun getNode(key: K): AvlNode<K, V>? {
        var curr = root
        while (curr != null) {
            if (curr.key == key) {
                return curr
            }
            curr = if (key < curr.key) curr.left else curr.right
        }
        return curr
    }

    override fun get(key: K): V? {
        val node = getNode(key)
        if (node == null || node.key != key) return null
        return node.value
    }

    override fun containsValue(value: V): Boolean {
        var contains = false
        root?.traverseInOrder { contains = contains || this.value == value }
        return contains
    }

    override fun containsKey(key: K): Boolean = getNode(key)?.let { it.key == key } ?: false
}

class AvlMutableMap<K : Comparable<K>, V> : MutableAvlTreeMapImpl<K, V>()

internal class AvlEntriesSet<K : Comparable<K>, V>(private val src: MutableAvlTreeMapImpl<K, V>) :
    AbstractMutableSet<MutableMap.MutableEntry<K, V>>() {
    override fun add(element: MutableMap.MutableEntry<K, V>): Boolean {
        return false
    }

    override val size: Int
        get() = TODO("Not yet implemented")

    override fun iterator(): MutableIterator<MutableMap.MutableEntry<K, V>> {
        TODO("Not yet implemented")
    }

}