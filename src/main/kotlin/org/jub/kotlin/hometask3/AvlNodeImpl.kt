package org.jub.kotlin.hometask3

import java.util.*

internal class AvlNodeImpl<K : Comparable<K>, V>(
    override val key: K,
    override var value: V
) : MutableMap.MutableEntry<K, V> {
    // public
    fun DEBUG_PRINT() {
        println(this)
        left?.DEBUG_PRINT()
        right?.DEBUG_PRINT()
    }

    override fun toString(): String =
        "Key: $key, Value: $value, Height: ${height}, Left key: ${left?.key}, Right key: ${right?.key}"

    var size: Int = 1
        private set

    override fun setValue(newValue: V): V {
        val old = value
        value = newValue
        return old
    }

    data class AvlReturn<K : Comparable<K>, V>(val value: V?, val newRoot: AvlNodeImpl<K, V>?)

    private infix fun V?.with(node: AvlNodeImpl<K, V>?) = AvlReturn(this, node)

    fun remove(key: K): AvlReturn<K, V> {
        val pathFromRoot = find(key)
        val nodeToRemove = pathFromRoot.pop() // at least `this` will be in the stack
        if (nodeToRemove.key != key) { // do nothing
            return null with this
        }
        val replacement = when {
            nodeToRemove.left == null -> nodeToRemove.right.also { nodeToRemove.right = null }
            nodeToRemove.right == null -> nodeToRemove.left.also { nodeToRemove.left = null }
            else -> { // both children are present
                val pathToReplacement =nodeToRemove.right!!.find(key)
                val candidate = pathToReplacement.pop() // leftmost node to the right
                if (pathToReplacement.isNotEmpty()) {
                    pathToReplacement.peek().left = candidate.right.also { candidate.right = null }
                    balanceAlongPath(pathToReplacement)
                } else {
                    nodeToRemove.right = null // we are taking the right child of the node to remove
                }
                candidate
            }
        }
        replacement?.right = nodeToRemove.right.also { nodeToRemove.right = null }
        replacement?.left = nodeToRemove.left.also { nodeToRemove.left = null }
        if (pathFromRoot.isEmpty()) {
            return nodeToRemove.value with replacement
        }
        val parentOfNodeToRemove = pathFromRoot.peek()
        when {
            parentOfNodeToRemove.key < key -> parentOfNodeToRemove.right = replacement
            else -> parentOfNodeToRemove.left = replacement
        }
        return nodeToRemove.value with balanceAlongPath(pathFromRoot)
    }

    fun put(key: K, value: V): AvlReturn<K, V> {
        val pathFromRoot = find(key) // at least `this` will be in the stack
        val candidate = pathFromRoot.peek()
        when {
            candidate.key < key -> candidate.right = AvlNodeImpl(key, value)
            candidate.key > key -> candidate.left = AvlNodeImpl(key, value)
            else -> {
                val old = candidate.value
                candidate.value = value
                return old with this
            }
        }
        return null with balanceAlongPath(pathFromRoot)
    }

    fun get(key: K): V? {
        val candidate = find(key).pop() // at least `this` will be in the stack
        return if (candidate.key != key) {
            null
        } else {
            candidate.value
        }
    }

    fun containsValue(value: V): Boolean {
        if (this.value == value) {
            return true
        }
        return left?.containsValue(value) ?: false || right?.containsValue(value) ?: false
    }

    fun asSequence(): Sequence<AvlNodeImpl<K, V>> = sequence {
        left?.let { yieldAll(it.asSequence()) }
        yield(this@AvlNodeImpl)
        right?.let { yieldAll(it.asSequence()) }
    }

    // private
    private var left: AvlNodeImpl<K, V>? = null
        set(value) {
            require(value?.let { it.key < key } ?: true) { "${value?.key} must be < $key to be left child" }
            field = value
            update()
        }
    private var right: AvlNodeImpl<K, V>? = null
        set(value) {
            require(value?.let { it.key > key } ?: true) { "${value?.key} must be > $key to be right child" }
            field = value
            update()
        }
    private var height: Int = 1
    private val balance: Int
        get() = (left?.height ?: 0) - (right?.height ?: 0)

    private fun update() {
        size = 1
        height = 1
        var leftHeight = 0
        var rightHeight = 0
        left?.let { l ->
            size += l.size
            leftHeight = l.height
        }
        right?.let { r ->
            size += r.size
            rightHeight = r.height
        }
        height = 1 + maxOf(leftHeight, rightHeight)
    }

    private fun find(key: K): Stack<AvlNodeImpl<K, V>> {
        val stack: Stack<AvlNodeImpl<K, V>> = Stack()
        var curr: AvlNodeImpl<K, V>? = this
        while (curr != null) {
            stack.push(curr)
            curr = when {
                curr.key == key -> return stack
                curr.key < key -> curr.right
                else -> curr.left
            }
        }
        return stack
    }

    private fun balanceAlongPath(changesPath: Stack<AvlNodeImpl<K, V>>): AvlNodeImpl<K, V> {
        require(changesPath.isNotEmpty()) { "Cannot balance along empty path" }
        while (changesPath.isNotEmpty()) {
            var curr = changesPath.pop()
            curr.update()
            when {
                changesPath.isEmpty() -> return curr.rotate()
                changesPath.peek().left == curr -> changesPath.peek().left = curr.rotate()
                else -> changesPath.peek().right = curr.rotate()
            }
        }
        error("Cannot balance along empty path")
    }

    private enum class AvlBalance(val factor: Int) {
        BALANCED(0),
        LEFT_ONE(1),
        LEFT_TWO(2),
        RIGHT_ONE(-1),
        RIGHT_TWO(-2)
    }

    private fun rotate() =
        when (balance) {
            AvlBalance.LEFT_TWO.factor -> {
                if (left?.balance == AvlBalance.RIGHT_ONE.factor) {
                    left = left?.leftRotate()
                }
                rightRotate()
            }

            AvlBalance.RIGHT_TWO.factor -> {
                if (right?.balance == AvlBalance.LEFT_ONE.factor) {
                    right = right?.rightRotate()
                }
                leftRotate()
            }

            AvlBalance.BALANCED.factor -> this
            AvlBalance.LEFT_ONE.factor -> this
            AvlBalance.RIGHT_ONE.factor -> this

            else -> error("Impossible balance factor: $balance")
        }

    private fun leftRotate(): AvlNodeImpl<K, V> {
        val child = right ?: error("Invalid rotate left: no right child.")
        right = child.left
        child.left = this
        return child
    }

    private fun rightRotate(): AvlNodeImpl<K, V> {
        val child = left ?: error("Invalid rotate right: no left child.")
        left = child.right
        child.right = this
        return child
    }
}