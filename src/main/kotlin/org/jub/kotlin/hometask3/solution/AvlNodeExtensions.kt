package org.jub.kotlin.hometask3.solution

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
