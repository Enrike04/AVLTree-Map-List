fun main() {
    val pattern = "ABCD"
    val text = "AABCDEFABCACDCABCD"
    var matches = 0
    outerLoop@for (i in text.indices) {
        innerLoop@for (j in pattern.indices) {
            if (text[i + j] != pattern[j]) {
                continue@outerLoop
                // break@outerLoop
            } else {
                if (j == 3) matches += 1
                continue@innerLoop
            }
        }
    }
    println(matches)
}