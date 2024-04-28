package org.jub.kotlin.hometask3

fun <K : Comparable<K>, V> getBst(collection: Iterable<Pair<K, V>>): BalancedSearchTree<K, V>{
    val avlTree = AvlTreeMutableMap<K, V>()
    collection.forEach{ (key, value) -> avlTree[key] = value }
    return avlTree
}

fun <K : Comparable<K>, V> getBstMap(collection: Iterable<Pair<K, V>>): BalancedSearchTreeMap<K, V>{
    val avlTree = AvlTreeMutableMap<K, V>()
    collection.forEach{ (key, value) -> avlTree[key] = value }
    return avlTree
}

fun <K : Comparable<K>, V> getMutableBstMap(collection: Iterable<Pair<K, V>>): MutableBalancedSearchTreeMap<K, V>{
    val avlTree = AvlTreeMutableMap<K, V>()
    collection.forEach{ (key, value) -> avlTree[key] = value }
    return avlTree
}

fun <K : Comparable<K>, V> getBstList(collection: Iterable<Pair<K, V>>): BalancedSearchTreeList<K, V> {
    val avlTree = AvlTreeMutableMap<K, V>()
    collection.forEach { (key, value) -> avlTree[key] = value }
    return AvlTreeList(avlTree)
}
