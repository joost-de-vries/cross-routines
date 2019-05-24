package colour.coroutine

import colour.Colour
import colour.orderToColour
import colour.println
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.*
import kotlin.coroutines.coroutineContext
import kotlin.jvm.Volatile


fun prln(c:Colour?,msg:String):Unit = when(c){
    null -> kotlin.io.println(msg)
    else -> println(c,msg)
}

class Order{
    private val mutex = Mutex()
    private val order = mutableMapOf<Job, Int>()
    suspend fun jobOrder(): Int {
        val job = coroutineContext[Job]!!
        return order[job] ?: mutex.withLock {
            val i = order.size
            order.put(job, i)
            i
        }
    }
    suspend fun prln(msg: String): Unit =
        prln(orderToColour[jobOrder()],msg)

    fun jobs():List<Job> =
        order.toList().sortedBy { it.second }.map { it.first }

}