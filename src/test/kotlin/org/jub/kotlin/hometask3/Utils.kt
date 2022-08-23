package org.jub.kotlin.hometask3

import kotlin.random.Random

internal const val testIterations = 10
internal const val testSetSize = 16

internal fun getSetOfRandomValues() = IntArray(testSetSize) { Random.nextInt() }.toSet()

typealias AvlTreeMapImpl<K, V> = BremenAvl<K, V>