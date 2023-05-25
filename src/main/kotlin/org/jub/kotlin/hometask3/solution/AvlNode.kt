package org.jub.kotlin.hometask3.solution

import kotlin.math.max

data class AvlNode<K : Comparable<K>, V>(
    override val key: K,
    override var value: V,
    var left: AvlNode<K, V>? = null,
    var right: AvlNode<K, V>? = null,
    var height: Int = 1
) : MutableMap.MutableEntry<K, V> {
    val rightHeight: Int
        get() = right?.height ?: 0

    val leftHeight: Int
        get() = left?.height ?: 0

    val balance: Int
        get() = leftHeight - rightHeight

    override fun setValue(newValue: V): V {
        val old = value
        value = newValue
        return old
    }

    data class RemoveResult<K : Comparable<K>, V>(val removedValue: V?, val newNode: AvlNode<K, V>?)

    private fun findReplacementChild(): AvlNode<K, V>? {
        left?.let {
            val replacement = it.maximumNode()
            val (v, newLeft) = it.remove(replacement.key)
            check(v != null && replacement.value == v) { "Could not remove replacement node from the tree" }
            replacement.right = right
            replacement.left = newLeft
            return replacement.balanced()
        }
        right?.let {
            val replacement = it.minimumNode()
            val (v, newRight) = it.remove(replacement.key)
            check(v != null && replacement.value == v) { "Could not remove replacement node from the tree" }
            replacement.right = newRight
            replacement.left = left // null
            return replacement.balanced()
        }
        return null
    }

    fun remove(keyToRemove: K): RemoveResult<K, V> {
        if (keyToRemove == key) {
            val replacement = findReplacementChild() ?: return RemoveResult(value, null)
            return RemoveResult(value, replacement)
        }

        val removedValue =
            if (keyToRemove < key) {
                val (v, newLeft) = left?.remove(keyToRemove) ?: return RemoveResult(null, null)
                left = newLeft
                v
            } else {
                val (v, newRight) = right?.remove(keyToRemove) ?: return RemoveResult(null, null)
                right = newRight
                v
            }

        // nothing was removed => no need to balance
        // if (removedValue == null) {
        //    return RemoveResult(null, this)
        // }

        return RemoveResult(removedValue, this.balanced())
    }
}
