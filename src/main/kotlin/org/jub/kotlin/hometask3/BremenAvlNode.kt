package org.jub.kotlin.hometask3

import java.util.*

internal class BremenAvlNode<K : Comparable<K>, V>(
    override val key: K,
    override var value: V
) : MutableMap.MutableEntry<K, V> {
    // public
    var size: Int = 1
        private set
    var left: BremenAvlNode<K, V>? = null
        set(value) {
            require(value?.let { it.key < key } ?: true) { "${value?.key} must be < $key to be left child" }
            field = value
            update()
        }
    var right: BremenAvlNode<K, V>? = null
        set(value) {
            require(value?.let { it.key > key } ?: true) { "${value?.key} must be > $key to be right child" }
            field = value
            update()
        }

    override fun setValue(newValue: V): V {
        val old = value
        value = newValue
        return old
    }

    data class AvlReturn<K : Comparable<K>, V>(val value: V?, val newRoot: BremenAvlNode<K, V>?)

    private infix fun V?.with(node: BremenAvlNode<K, V>?) = AvlReturn(this, node)

    fun remove(key: K): AvlReturn<K, V> {
        val pathFromRoot = find(key)
        val nodeToRemove = pathFromRoot.pop() // at least `this` will be in the stack
        if (nodeToRemove.key != key) { // do nothing
            return null with this
        }
        val replacement: BremenAvlNode<K, V>?
        when {
            nodeToRemove.left == null -> {
                replacement = nodeToRemove.right
                nodeToRemove.right = null
            }

            nodeToRemove.right == null -> {
                replacement = nodeToRemove.left
                nodeToRemove.left = null
            }

            else -> { // both children are present
                val candidate = nodeToRemove.right!!.leftmost()
                val newRight = nodeToRemove.right!!.remove(candidate.key).newRoot // can shorten by 1
                candidate.left = nodeToRemove.left
                candidate.right = newRight
                replacement = candidate.rotate()
            }
        }
        nodeToRemove.right = null
        nodeToRemove.left = null
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
            candidate.key < key -> candidate.right = BremenAvlNode(key, value)
            candidate.key > key -> candidate.left = BremenAvlNode(key, value)
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
    
    // Uses recursion :(
    fun containsValue(value: V): Boolean {
        if (this.value == value) {
            return true
        }
        return left?.containsValue(value) ?: false || right?.containsValue(value) ?: false
    }

    fun asSequence(): Sequence<BremenAvlNode<K, V>> = sequence {
        left?.let { yieldAll(it.asSequence()) }
        yield(this@BremenAvlNode)
        right?.let { yieldAll(it.asSequence()) }
    }

    fun leftmost(): BremenAvlNode<K, V> {
        var curr = this
        while (curr.left != null) {
            curr = curr.left!!
        }
        return curr
    }

    fun rightmost(): BremenAvlNode<K, V> {
        var curr = this
        while (curr.right != null) {
            curr = curr.right!!
        }
        return curr
    }

    // private
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

    private fun find(key: K): Stack<BremenAvlNode<K, V>> {
        val stack: Stack<BremenAvlNode<K, V>> = Stack()
        var curr: BremenAvlNode<K, V>? = this
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

    private fun balanceAlongPath(changesPath: Stack<BremenAvlNode<K, V>>): BremenAvlNode<K, V> {
        require(changesPath.isNotEmpty()) { "Cannot balance along empty path" }
        while (changesPath.isNotEmpty()) {
            val curr = changesPath.pop()
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

    private fun leftRotate(): BremenAvlNode<K, V> {
        val child = right ?: error("Invalid rotate left: no right child.")
        right = child.left
        child.left = this
        return child
    }

    private fun rightRotate(): BremenAvlNode<K, V> {
        val child = left ?: error("Invalid rotate right: no left child.")
        left = child.right
        child.right = this
        return child
    }
}