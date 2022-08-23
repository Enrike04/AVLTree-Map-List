fun main() {
var readonly = listOf(1, 2, 3)
val mutable = mutableListOf(1, 2, 3)
mutable.add(4)
readonly = listOf(1, 2, 3, 4)
    mutable.clear()
    println(mutable[0])
}