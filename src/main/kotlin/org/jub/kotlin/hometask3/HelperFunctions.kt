package org.jub.kotlin.hometask3

import org.jub.kotlin.hometask3.solution.AvlMutableMap
import org.jub.kotlin.hometask3.solution.BaseAvl

fun <K : Comparable<K>, V> getAvlTree(collection: Iterable<Pair<K, V>>): AvlTree<K, V> {
    val base = BaseAvl<K, V>()
    collection.forEach { (k, v) ->
        base.insert(k, v)
    }
    return base
}

fun <K : Comparable<K>, V> getAvlTreeMap(collection: Iterable<Pair<K, V>>): AvlTreeMap<K, V> {
    val avl = AvlMutableMap<K, V>()
    collection.forEach { (k, v) ->
        avl.insert(k, v)
    }
    return avl
}

fun <K : Comparable<K>, V> getMutableAvlTreeMap(collection: Iterable<Pair<K, V>>): MutableAvlTreeMap<K, V> {
    val avl = AvlMutableMap<K, V>()
    collection.forEach { (k, v) ->
        avl.insert(k, v)
    }
    return avl
}

fun <K : Comparable<K>, V> getAvlTreeList(collection: Iterable<Pair<K, V>>): AvlTreeList<K, V> = TODO()
