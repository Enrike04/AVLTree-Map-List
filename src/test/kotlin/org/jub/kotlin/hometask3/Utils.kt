package org.jub.kotlin.hometask3

import kotlin.random.Random

internal const val TEST_ITERATIONS = 10
internal const val TEST_SET_SIZE = 128

internal fun getSetOfRandomValues(size: Int = TEST_SET_SIZE) = IntArray(size) { Random.nextInt() }.toSet()
