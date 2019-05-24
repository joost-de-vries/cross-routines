import kotlinx.coroutines.*
import kotlinx.coroutines.async
import java.time.Duration
import java.time.Instant


fun main() = runBlocking {
    // this: CoroutineScope
    val productsSeq: List<Product> = sequential()

    val productsPar: List<Product> = parallel()

    val t = Timer()
    val products2Job = launch { parallel() }
    delay(10)
    products2Job.cancelAndJoin()
    println("After 2nd parallel ${t.took()}")

}

suspend fun sequential(): List<Product> {
    val t = Timer()
    val productNames: List<String> = getProductNames()

    val result: List<Product> = productNames.map { getProduct(it) }
    println("sequential took ${t.took()}")
    return result
}

suspend fun parallel(): List<Product> {
    val t = Timer()
    val names = getProductNames()

    val products = coroutineScope {
        names.map { n ->
            async { getProduct(n) }
        }.map { dp: Deferred<Product> ->
            dp.await()
        }
    }
    println("parallel took ${t.took()}")
    return products
}

suspend fun getProductNames(): List<String> {
    delay(100)
    println("return product names")
    return listOf("insurance", "loan", "checking account")
}

suspend fun getProduct(name: String): Product {
    delay(500)
    println("returning $name")
    return Product(name)
}

data class Product(val name: String)


class Timer {
    private var start = Instant.now()
    fun took() = Duration.between(start, Instant.now()).toMillis()
}