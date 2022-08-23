package org.jub.kotlin.hometask3

interface AvlTree<K : Comparable<K>, V> {
    /**
     * All maximum/minimum funs should throw some Exception if the tree is empty
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
interface AvlTreeMap<K : Comparable<K>, V> : Map<K, V>, AvlTree<K, V>

interface MutableAvlTreeMap<K : Comparable<K>, V> :  MutableMap<K, V>, AvlTreeMap<K, V>

interface AvlTreeList<K : Comparable<K>, V> : List<V>, AvlTree<K, V>