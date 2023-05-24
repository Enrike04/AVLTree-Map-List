fun main() {
//    val avl = BaseAvlTreeImpl<Int, Int>()
//    List(10) { it to Random.nextInt(100) }.forEach {
//        avl.insert(it.first, it.second)
//    }
//    avl.insert(-100, 1)
//    avl.insert(666, 1)
//    avl.root?.traverseInOrder {
//        print("${it}; ")
//    }

    val map = mutableMapOf(1 to 2, 3 to 4)
    val entr = map.entries
    entr.forEach { print("$it; ") }
    println(".")
    entr.removeIf { it.key == 1 }
    entr.forEach { print("$it; ") }
    println(".")
    map.forEach { print("$it; ") }
}
