package org.jub.kotlin.hometask3

interface AvlTree<K : Comparable<K>, V> : Collection<V> {
    fun maximumKey(): K

    fun minimumKey(): K

    fun maximumValue(): V

    fun mimimumValue(): V
}

interface MutableAvlTree<K : Comparable<K>, V> : Collection<V>

interface AvlTreeMap<K : Comparable<K>, V> : Map<K, V>, AvlTree<K, V>

interface MutableAvlTreeMap<K : Comparable<K>, V> :  MutableMap<K, V>, AvlTree<K, V>

interface AvlTreeList<K : Comparable<K>, V> : List<V>, AvlTree<K, V>

interface MutableAvlTreeList<K : Comparable<K>, V> : MutableList<V>, AvlTree<K, V>