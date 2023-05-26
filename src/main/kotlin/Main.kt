import org.jub.kotlin.hometask3.getMutableAvlTreeMap
import org.jub.kotlin.hometask3.solution.MutableAvlTreeMapImpl
import kotlin.random.Random

fun main() {
    val wow = List(10) { it to Random.nextInt(100) }
    val avl = getMutableAvlTreeMap(wow)
    println(avl.entries.size)
    for (element in avl.entries) {
        println(element)
    }

//    val map = mutableMapOf(1 to 2, 3 to 4)
//    val entr = map.entries
//    entr.forEach { print("$it; ") }
//    println(".")
//    entr.removeIf { it.key == 1 }
//    entr.forEach { print("$it; ") }
//    println(".")
//    map.forEach { print("$it; ") }
}
