package org.jub.kotlin.hometask3.solution

import kotlin.math.max

data class AvlNode<K : Comparable<K>, V>(
    val key: K,
    var value: V,
    var left: AvlNode<K, V>? = null,
    var right: AvlNode<K, V>? = null,
    var height: Int = 1
) {
    val rightHeight: Int
        get() = right?.height ?: 0

    val leftHeight: Int
        get() = left?.height ?: 0

    val balance: Int
        get() = leftHeight - rightHeight

    fun updateHeight() {
        height = max(leftHeight, rightHeight) + 1
    }

    fun traverseInOrder(visit: AvlNode<K, V>.() -> Unit) {
        left?.traverseInOrder(visit)
        visit()
        right?.traverseInOrder(visit)
    }

    fun traversePreOrder(visit: AvlNode<K, V>.() -> Unit) {
        visit()
        left?.traversePreOrder(visit)
        right?.traversePreOrder(visit)
    }

    fun traversePostOrder(visit: AvlNode<K, V>.() -> Unit) {
        left?.traversePostOrder(visit)
        right?.traversePostOrder(visit)
        visit()
    }
}
