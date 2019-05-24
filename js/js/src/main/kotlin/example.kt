
import kotlinx.coroutines.*
import kotlinx.coroutines.async

// go to build/classes/kotlin/main/js.js to see generated code
suspend fun sequential():List<Product>  {

val productNames = getProductNames()

return productNames.map{getProduct(it)}
}

suspend fun getProductNames(): List<String> {
    println("before suspension point")
    delay(100)
    println("after suspension point")
    return listOf("insurance", "loan", "checking account")
}

suspend fun getProduct(name: String): Product {
    delay(500)
    return Product(name)
}
data class Product(val name:String)