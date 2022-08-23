package org.jub.kotlin.hometask3

class AvlTreeImplVerify<K : Comparable<K>, V> : MutableMap<K, V> {
    fun DEBUG_PRINT() {
        root?.DEBUG_PRINT()
    }

    override val entries: MutableSet<MutableMap.MutableEntry<K, V>>
        get() = (root?.asSequence() ?: emptySequence()).toMutableSet()
    override val keys: MutableSet<K>
        get() = (root?.asSequence() ?: emptySequence()).map { it.key }.toMutableSet()
    override val size: Int
        get() = root?.size ?: 0
    override val values: MutableList<V>
        get() = (root?.asSequence() ?: emptySequence()).map { it.value }.toMutableList()

    private var root: AvlNodeImplVerify<K, V>? = null

    override fun clear() {
        root = null
    }

    override fun isEmpty() = root == null

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
        root = AvlNodeImplVerify(key, value)
        return null
    }

    override fun get(key: K): V? = root?.get(key)

    override fun containsValue(value: V): Boolean = root?.containsValue(value) ?: false

    override fun containsKey(key: K): Boolean = get(key) != null
}