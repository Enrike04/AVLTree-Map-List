package org.jub.kotlin.hometask3

interface BalancedSearchTree<K : Comparable<K>, V> {
    /* abstract */ val height: Int

    /**
     * All maximum/minimum functions should throw some Exception if the tree is empty
     */
    fun maximumKey(): K
    fun minimumKey(): K
    fun maximumValue(): V
    fun minimumValue(): V
}

/**
 * We would strongly advise to implement Mutable Version right from the start. This way constructor from a Collection of
 * Pairs can simply call put for each pair. Otherwise, you should write a constructor yourself.
 */
interface BalancedSearchTreeMap<K : Comparable<K>, V> : Map<K, V>, BalancedSearchTree<K, V>

interface MutableBalancedSearchTreeMap<K : Comparable<K>, V> : MutableMap<K, V>, BalancedSearchTreeMap<K, V> {
    /**
     * Type parameters of `other` here and in `mergeWith` below are wrong. Fix, please
     * This method has a requirement that keys of `other` should be larger than keys of `this`, otherwise an Exception
     * should be thrown.
     */
    fun merge(other: MutableBalancedSearchTreeMap<out K, out V>): MutableBalancedSearchTreeMap<K, V>
}

infix fun <K : Comparable<K>, V> MutableBalancedSearchTreeMap<K, V>.mergeWith(other: MutableBalancedSearchTreeMap<out K, out V>) =
    merge(other)

/**
 * AbstractIterator might help you implement Iterator.
 * An easy way to implement would be to store index and call List::get(index), but we don't like easy ways.
 */
interface BalancedSearchTreeList<K : Comparable<K>, V> : List<V>, BalancedSearchTree<K, V>

class AvlNode<K : Comparable<K>, V>(override var key: K, override var value: V, var height: Int = 1) :
    MutableMap.MutableEntry<K, V> {
    var left: AvlNode<K, V>? = null
    var right: AvlNode<K, V>? = null
    var parent: AvlNode<K, V>? = null
    override fun setValue(newValue: V): V = value.also { value = newValue }
    fun findMaximum(): AvlNode<K, V>? {
        var cur: AvlNode<K, V>? = this
        while (cur?.right != null) {
            cur = cur.right
        }
        return cur
    }

    fun findMinimum(): AvlNode<K, V>? {
        var cur: AvlNode<K, V>? = this
        while (cur?.left != null) {
            cur = cur.left
        }
        return cur
    }

    fun goNext(): AvlNode<K, V>? {
        var next: AvlNode<K, V>? = this.right?.findMinimum()
        return next ?: run {
            next = this
            while (next!!.parent != null) {
                if (next!!.parent!!.key > next!!.key) {
                    break
                }
                next = next!!.parent
            }
            next = next!!.parent
            next
        }
    }
    override fun toString(): String = "$key=$value"
}

abstract class AvlTreeAbstract<K : Comparable<K>, V> : BalancedSearchTree<K, V> {
    var size = 0
    protected fun getHeightNode(node: AvlNode<K, V>?): Int = node?.height ?: 0
    private fun updateHeightNode(node: AvlNode<K, V>) {
        node.height = 1 + maxOf(getHeightNode(node.left), getHeightNode(node.right))
    }

    private fun balanceFactor(node: AvlNode<K, V>?): Int = getHeightNode(node?.left) - getHeightNode(node?.right)

    private fun rotateLeft(node: AvlNode<K, V>): AvlNode<K, V> {
        val rightChild = node.right!!
        val leftSubtreeOfRightChild = rightChild.left

        rightChild.left = node
        node.right = leftSubtreeOfRightChild

        rightChild.parent = node.parent
        node.parent = rightChild
        leftSubtreeOfRightChild?.parent = node

        updateHeightNode(node)
        updateHeightNode(rightChild)

        return rightChild
    }

    private fun rotateRight(node: AvlNode<K, V>): AvlNode<K, V> {
        val leftChild = node.left!!
        val rightSubtreeOfLeftChild = leftChild.right

        leftChild.right = node
        node.left = rightSubtreeOfLeftChild
        leftChild.parent = node.parent
        node.parent = leftChild
        rightSubtreeOfLeftChild?.parent = node
        updateHeightNode(node)
        updateHeightNode(leftChild)
        return leftChild
    }

    private fun rebalance(node: AvlNode<K, V>): AvlNode<K, V> {
        updateHeightNode(node)
        val balance = balanceFactor(node)
        if (balance > 1) {
            return if (balanceFactor(node.left) >= 0) {
                rotateRight(node)
            } else {
                node.left = rotateLeft(node.left!!)
                rotateRight(node)
            }
        }
        if (balance < -1) {
            return if (balanceFactor(node.right) <= 0) {
                rotateLeft(node)
            } else {
                node.right = rotateRight(node.right!!)
                rotateLeft(node)
            }
        }
        return node
    }

    protected fun insert(
        node: AvlNode<K, V>?,
        key: K,
        value: V,
        parent: AvlNode<K, V>? = null
    ): AvlNode<K, V> {
        node ?: return AvlNode(key, value).apply {
            this.parent = parent
        }

        when {
            key < node.key -> {
                node.left = insert(node.left, key, value, node)
            }

            key > node.key -> {
                node.right = insert(node.right, key, value, node)
            }

            else -> {
                node.value = value
                return node
            }
        }
        return rebalance(node)
    }
    protected fun delete(root: AvlNode<K, V>?, key: K): AvlNode<K, V>? {
        root ?: return null
        var newRoot: AvlNode<K, V>? = root
        when {
            key < root.key -> newRoot?.left = delete(root.left, key)
            key > root.key -> newRoot?.right = delete(root.right, key)
            else -> {
                if (root.left == null || root.right == null) {
                    val temp = root.left ?: root.right
                    temp?.parent = root.parent
                    newRoot = temp
                } else {
                    val temp = root.right!!.findMinimum()
                    root.key = temp!!.key
                    root.value = temp.value ?: root.value
                    root.right = delete(root.right, root.key)
                    newRoot = root
                }
            }
        }
        return newRoot?.let { rebalance(newRoot) }
    }
    protected fun getNode(node: AvlNode<K, V>?, key: K): AvlNode<K, V>? {
        node ?: return null
        return if (key < node.key) getNode(node.left, key)
        else if (key > node.key) getNode(node.right, key)
        else node
    }
}

sealed class AvlTree<K : Comparable<K>, V> : AvlTreeAbstract<K, V>() {
    private var root: AvlNode<K, V>? = null
    var countModifications = 0
    override val height: Int
        get() = super.getHeightNode(root)

    fun delete(key: K) {
        getKeyNode(key)?.also { size-- }
        root = delete(this.root, key)
        countModifications++
    }

    fun removeAll() {
        root = null
        countModifications = 0
        size = 0
    }

    fun getKeyNode(key: K): AvlNode<K, V>? = super.getNode(this.root, key)

    fun insert(key: K, value: V) {
        getKeyNode(key) ?: size++
        root = insert(this.root, key, value)
        countModifications++
    }

    override fun maximumKey(): K = root?.findMaximum()?.key ?: throw NoSuchElementException("Key not found!")

    override fun minimumKey(): K = root?.findMinimum()?.key ?: throw NoSuchElementException("Key not found!")

    override fun maximumValue(): V = root?.findMaximum()?.value ?: throw NoSuchElementException("Value not found!")

    override fun minimumValue(): V = root?.findMinimum()?.value ?: throw NoSuchElementException("Value not found!")

    fun minimumNode(): AvlNode<K, V>? = root?.findMinimum()
}

internal class AvlTreeIterator<K : Comparable<K>, V>(val data: AvlTree<K, V>) : MutableIterator<MutableMap.MutableEntry<K, V>> {
    private var cur = data.minimumNode()
    private var count = data.countModifications
    private var lastReturn: AvlNode<K, V>? = null
    fun checkKur() {
        if (count != data.countModifications) {
            throw ConcurrentModificationException("Data modified externally")
        }
    }

    override fun hasNext(): Boolean = cur != null

    override fun next(): MutableMap.MutableEntry<K, V> {
        checkKur()
        if (hasNext()) {
            lastReturn = cur
            cur = cur?.goNext()
            return lastReturn ?: throw ConcurrentModificationException("Error")
        }
        error("Error message")
    }

    override fun remove() {
        checkKur()
        lastReturn?.also {
            data.delete(it.key)
            lastReturn = null
            count++
        } ?: error("Error message 1")
    }
}

internal class AvlMapEntries<K : Comparable<K>, V>(private val data: AvlTree<K, V>) : MutableSet<MutableMap.MutableEntry<K, V>> {
    override val size: Int get() = data.size
    override fun clear() {
        data.removeAll()
    }

    override fun isEmpty(): Boolean = size == 0

    override fun iterator(): MutableIterator<MutableMap.MutableEntry<K, V>> = AvlTreeIterator(data)

    override fun add(element: MutableMap.MutableEntry<K, V>): Boolean = throw UnsupportedOperationException()

    override fun addAll(elements: Collection<MutableMap.MutableEntry<K, V>>): Boolean = throw UnsupportedOperationException()

    override fun contains(element: MutableMap.MutableEntry<K, V>): Boolean {
        data.getKeyNode(element.key)?.also {
            return if (it.value == element.value) {
                true
            } else {
                false
            }
        }
        return false
    }

    override fun containsAll(elements: Collection<MutableMap.MutableEntry<K, V>>): Boolean = elements.all { contains(it) }

    override fun remove(element: MutableMap.MutableEntry<K, V>): Boolean {
        data.getKeyNode(element.key)?.also {
            return if (it.value == element.value) {
                data.delete(element.key)
                true
            } else {
                false
            }
        }
        return false
    }

    override fun removeAll(elements: Collection<MutableMap.MutableEntry<K, V>>): Boolean = elements.any { remove(it) }

    override fun retainAll(elements: Collection<MutableMap.MutableEntry<K, V>>): Boolean {
        val mark = mutableListOf<K>()
        for (node in iterator()) {
            if (node !in elements) {
                mark += node.key
            }
        }
        for (key in mark) {
            mark.remove(key)
        }

        return mark.isNotEmpty()
    }

    override fun toString(): String = this.joinToString(prefix = "{", postfix = "}", separator = ", ")
}

internal class AvlMapKeys<K : Comparable<K>, V>(private val data: AvlTree<K, V>) : MutableSet<K> {
    override val size: Int get() = data.size

    override fun iterator(): MutableIterator<K> = IteratorGetKey(AvlTreeIterator(data))
    override fun clear() = data.removeAll()
    override fun isEmpty(): Boolean = size == 0
    override fun contains(element: K): Boolean = data.getKeyNode(element)?.let { true } ?: false
    override fun containsAll(elements: Collection<K>): Boolean = elements.all { contains(it) }
    override fun remove(element: K): Boolean = data.getKeyNode(element)?.let { true } ?: false
    override fun retainAll(elements: Collection<K>): Boolean = iterator()
        .asSequence()
        .filter { it !in elements }
        .onEach { remove(it) }
        .toList()
        .isNotEmpty()

    override fun removeAll(elements: Collection<K>): Boolean = elements.any { remove(it) }
    override fun addAll(elements: Collection<K>): Boolean = throw UnsupportedOperationException()
    override fun add(element: K): Boolean = throw UnsupportedOperationException()
    inner class IteratorGetKey<K>(private val iterator: MutableIterator<MutableMap.MutableEntry<K, *>>) :
        MutableIterator<K> {
        override fun next(): K = iterator.next().key
        override fun hasNext(): Boolean = iterator.hasNext()
        override fun remove() = iterator.remove()
    }
}
internal class AvlMapValues<K : Comparable<K>, V>(private val data: AvlTree<K, V>) : MutableCollection<V> {
    override val size: Int
        get() = data.size

    override fun clear() = data.removeAll()

    override fun isEmpty(): Boolean = size == 0

    override fun iterator(): MutableIterator<V> = IteratorGetValue(AvlTreeIterator(data))

    override fun add(element: V): Boolean = throw UnsupportedOperationException()

    override fun addAll(elements: Collection<V>): Boolean = throw UnsupportedOperationException()

    override fun contains(element: V): Boolean {
        for (value in iterator()) {
            if (value == element) return true
        }
        return false
    }

    override fun containsAll(elements: Collection<V>): Boolean = elements.all { contains(it) }

    override fun remove(element: V): Boolean {
        for ((key, value) in AvlTreeIterator(data)) {
            if (value == element) {
                data.delete(key)
                return true
            }
        }
        return false
    }

    override fun removeAll(elements: Collection<V>): Boolean = elements.all { remove(it) }

    override fun retainAll(elements: Collection<V>): Boolean {
        val mark = mutableListOf<K>()
        for ((key, value) in AvlTreeIterator(data)) {
            if (value !in elements) {
                mark += key
            }
        }
        for (key in mark) {
            data.delete(key)
        }
        return mark.isNotEmpty()
    }

    inner class IteratorGetValue<V>(private val iterator: MutableIterator<MutableMap.MutableEntry<*, V>>) :
        MutableIterator<V> {
        override fun next(): V = iterator.next().value

        override fun hasNext(): Boolean = iterator.hasNext()

        override fun remove() = iterator.remove()
    }
}
class AvlTreeMutableMap<K : Comparable<K>, V> : MutableBalancedSearchTreeMap<K, V>, AvlTree<K, V>() {
    override val entries: MutableSet<MutableMap.MutableEntry<K, V>> = AvlMapEntries(this)
    override val keys: MutableSet<K> = AvlMapKeys(this)
    override val values: MutableCollection<V> = AvlMapValues(this)
    override val height: Int
        get() = super.height
    override fun get(key: K): V? = super.getKeyNode(key)?.value
    override fun isEmpty(): Boolean = size == 0
    override fun containsKey(key: K): Boolean = get(key) != null

    override fun merge(other: MutableBalancedSearchTreeMap<out K, out V>): MutableBalancedSearchTreeMap<K, V> {
        if (other.minimumKey() <= this.maximumKey()) {
            error("other min key is smaller than this.maximumKey")
        }
        for ((key, value) in other) {
            put(key, value)
        }
        return this
    }

    override fun put(key: K, value: V): V? {
        val result = get(key)
        insert(key, value)
        return result
    }

    override fun putAll(from: Map<out K, V>) {
        for ((key, value) in from) {
            put(key, value)
        }
    }

    override fun clear() = removeAll()

    override fun containsValue(value: V): Boolean = values.contains(value)

    override fun remove(key: K): V? {
        val result = get(key)
        delete(key)
        return result
    }
}

abstract class TreeListBstFunctionality<K : Comparable<K>, V>(protected val data: AvlTreeMutableMap<K, V>) :
    BalancedSearchTree<K, V> {
    override val height: Int
        get() = data.height

    override fun maximumKey(): K = data.maximumKey()

    override fun maximumValue(): V = data.maximumValue()

    override fun minimumKey(): K = data.minimumKey()

    override fun minimumValue(): V = data.minimumValue()
}

class AvlTreeList<K : Comparable<K>, V>(data: AvlTreeMutableMap<K, V>) : TreeListBstFunctionality<K, V>(data),
    BalancedSearchTreeList<K, V> {
    override val size: Int
        get() = data.size

    private val list = data.values.toList()
    override fun contains(element: V): Boolean = list.contains(element)

    override fun containsAll(elements: Collection<V>): Boolean = list.containsAll(elements)

    override fun get(index: Int): V = list[index]

    override fun indexOf(element: V): Int = list.indexOf(element)

    override fun lastIndexOf(element: V): Int = list.lastIndexOf(element)

    override fun listIterator(index: Int): ListIterator<V> = list.listIterator(index)

    override fun isEmpty(): Boolean = list.isEmpty()

    override fun iterator(): Iterator<V> = list.iterator()

    override fun listIterator(): ListIterator<V> = list.listIterator()

    override fun subList(fromIndex: Int, toIndex: Int): List<V> = list.subList(fromIndex, toIndex)
}
