
fun main(){
    println("create sequence")
    val seq = fibonacci1()
        .map { v -> //intermediate op
        println("working: multiplying")
        v * 2 }
        .take(3)

    println("about to start work")

    seq.forEach { //terminal op
        println("work result: v $it") }
}

fun fibonacci1(): Sequence<Int> = fibPairs().map { it.first }

fun fibPairs() = generateSequence(Pair(0, 1)) { pair ->
    println("working: Next pair")
    Pair(pair.second, pair.first + pair.second)
}

fun fibonacci2() = sequence {
    var terms = Pair(0, 1)

    // this sequence is infinite
    while (true) {
        yield(terms.first)
        terms = Pair(terms.second, terms.first + terms.second)
    }
}
