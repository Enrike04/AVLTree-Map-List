package org.jub.kotlin.hometask3.solution

import kotlin.math.max

fun <K : Comparable<K>, V> AvlNode<K, V>.minimumNode(): AvlNode<K, V> {
    var node = this
    while (true) {
        node = node.left ?: break
    }
    return node
}

fun <K : Comparable<K>, V> AvlNode<K, V>.maximumNode(): AvlNode<K, V> {
    var node = this
    while (true) {
        node = node.right ?: break
    }
    return node
}

fun <K : Comparable<K>, V> AvlNode<K, V>.leftRotate(): AvlNode<K, V> {
    val pivot = right!!
    right = pivot.left
    pivot.left = this
    updateHeight()
    pivot.updateHeight()
    return pivot
}

fun <K : Comparable<K>, V> AvlNode<K, V>.rightRotate(): AvlNode<K, V> {
    val pivot = left!!
    left = pivot.right
    pivot.right = this
    updateHeight()
    pivot.updateHeight()
    return pivot
}

fun <K : Comparable<K>, V> AvlNode<K, V>.rightLeftRotate(): AvlNode<K, V> {
    val rightChild = right ?: return this
    right = rightChild.rightRotate()
    return leftRotate()
}

fun <K : Comparable<K>, V> AvlNode<K, V>.leftRightRotate(): AvlNode<K, V> {
    val leftChild = left ?: return this
    left = leftChild.leftRotate()
    return rightRotate()
}

fun <K : Comparable<K>, V> AvlNode<K, V>.balanced(): AvlNode<K, V> {
    return when (balance) {
        2 -> {
            if (left?.balance == -1) {
                leftRightRotate()
            } else {
                rightRotate()
            }
        }

        -2 -> {
            if (right?.balance == 1) {
                rightLeftRotate()
            } else {
                leftRotate()
            }
        }

        else -> this
    }
}

fun <K : Comparable<K>, V> AvlNode<K, V>.updateHeight() {
    height = max(leftHeight, rightHeight) + 1
}

fun <K : Comparable<K>, V> AvlNode<K, V>.traverseInOrder(visit: AvlNode<K, V>.() -> Unit) {
    left?.traverseInOrder(visit)
    visit()
    right?.traverseInOrder(visit)
}

fun <K : Comparable<K>, V> AvlNode<K, V>.traversePreOrder(visit: AvlNode<K, V>.() -> Unit) {
    visit()
    left?.traversePreOrder(visit)
    right?.traversePreOrder(visit)
}

fun <K : Comparable<K>, V> AvlNode<K, V>.traversePostOrder(visit: AvlNode<K, V>.() -> Unit) {
    left?.traversePostOrder(visit)
    right?.traversePostOrder(visit)
    visit()
}
