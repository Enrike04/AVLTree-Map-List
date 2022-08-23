package org.jub.kotlin.hometask3

class BremenAvl<K : Comparable<K>, V> : MutableAvlTreeMap<K, V> {
    override val entries: MutableSet<MutableMap.MutableEntry<K, V>>
        get() = (root?.asSequence() ?: emptySequence()).toMutableSet()
    override val keys: MutableSet<K>
        get() = (root?.asSequence() ?: emptySequence()).map { it.key }.toMutableSet()
    override val size: Int
        get() = root?.size ?: 0
    override val values: MutableList<V>
        get() = (root?.asSequence() ?: emptySequence()).map { it.value }.toMutableList()

    private var root: BremenAvlNode<K, V>? = null

    override fun clear() {
        root = null
    }

    override fun isEmpty() = root == null
    override fun maximumKey(): K = root?.rightmost()?.key ?: error("The tree is empty")

    override fun minimumKey(): K = root?.leftmost()?.key ?: error("The tree is empty")

    override fun maximumValue(): V = root?.rightmost()?.value ?: error("The tree is empty")

    override fun mimimumValue(): V = root?.leftmost()?.value ?: error("The tree is empty")

    override fun remove(key: K): V? =
        root?.let {
            val (removedValue, newRoot) = root!!.remove(key)
            root = newRoot
            removedValue
        }

    // Can be written better
    override fun putAll(from: Map<out K, V>) =
        from.forEach { (key, value) ->
            put(key, value)
        }

    override fun put(key: K, value: V): V? {
        root?.let {
            val (oldValue, newRoot) = root!!.put(key, value)
            root = newRoot
            return oldValue
        }
        root = BremenAvlNode(key, value)
        return null
    }

    override fun get(key: K): V? = root?.get(key)

    override fun containsValue(value: V): Boolean = root?.containsValue(value) ?: false

    override fun containsKey(key: K): Boolean = get(key) != null
}