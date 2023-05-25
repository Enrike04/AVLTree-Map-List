package org.jub.kotlin.hometask3.solution

import org.jub.kotlin.hometask3.AvlTree
import java.util.ConcurrentModificationException

sealed class BaseAvlTreeImpl<K : Comparable<K>, V> : AvlTree<K, V> {
    protected inner class Result(val node: AvlNode<K, V>, val value: V?) {
        operator fun component1() = node
        operator fun component2() = value
    }

    protected infix fun AvlNode<K, V>.with(value: V?) = Result(this, value)

    protected var root: AvlNode<K, V>? = null

    protected var modCount: Int = 0
        private set

    var size: Int = 0
        private set

    protected val rootOrException: AvlNode<K, V>
        get() = root ?: throw IllegalArgumentException("Empty tree")

    override val height: Int
        get() = root?.height ?: 0

    override fun maximumKey() = rootOrException.maximumNode().key

    override fun minimumKey() = rootOrException.minimumNode().key

    override fun maximumValue() = rootOrException.maximumNode().value

    override fun minimumValue() = rootOrException.minimumNode().value

    fun insert(key: K, value: V): V? {
        val (n, v) = insert(root, AvlNode(key, value))
        root = n
        v ?: run {
            size += 1
            modCount += 1
        }
        return v
    }

    protected fun pathToKey(key: K): ArrayDeque<AvlNode<K, V>> {
        val path = ArrayDeque<AvlNode<K, V>>()
        var node = root
        while (node != null) {
            path.add(node)
            if (node.key == key) break
            node =
                if (node.key < key) {
                    node.right
                } else {
                    node.left
                }
        }
        return path
    }

    fun clear() {
        root = null
        size = 0
        modCount += 1
    }

    private fun insert(treeNode: AvlNode<K, V>?, newNode: AvlNode<K, V>): Result {
        var oldValue: V? = null
        treeNode ?: return newNode with oldValue
        if (newNode.key == treeNode.key) {
            oldValue = treeNode.value
            treeNode.value = newNode.value
            return treeNode with oldValue
        }
        if (newNode.key < treeNode.key) {
            val (n, v) = insert(treeNode.left, newNode)
            treeNode.left = n
            oldValue = v
        } else {
            val (n, v) = insert(treeNode.right, newNode)
            treeNode.right = n
            oldValue = v
        }
        treeNode.updateHeight()
        return treeNode.balanced() with oldValue
    }

    fun remove(key: K): V? {
        val result = root?.remove(key)
        result?.removedValue?.let {
            root = result.newNode
            modCount += 1
            size -= 1
            return it
        }
        return null
    }

    internal inner class MutableAvlIterator :
        AbstractIterator<AvlNode<K, V>>(), MutableIterator<AvlNode<K, V>> {
        private var modCount = this@BaseAvlTreeImpl.modCount

        private fun checkConcurrentModification() {
            if (this@BaseAvlTreeImpl.modCount != modCount) {
                throw ConcurrentModificationException()
            }
        }
        override fun computeNext() {
            checkConcurrentModification()
            TODO("Not yet implemented")
        }

        // TODO: throw IllegalStateException when computeNext() was not called yet
        override fun remove() {
            checkConcurrentModification()
            TODO("Not yet implemented")
        }
    }
}

class BaseAvl<K : Comparable<K>, V> : BaseAvlTreeImpl<K, V>()
