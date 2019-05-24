package colour.coroutine.columns

import colour.coroutine.Order
import colour.orderToColour


class ColumnedCoroutine(
    private val columned :Columned= Columned()
){
    private val order = Order()

    suspend fun prln(msg:String): Unit {
        val o = order.jobOrder()
        val lines = columned.toColumnLines(column = o, msg = msg)
        lines.forEach { colour.coroutine.prln(orderToColour[o],it) }
    }

}

class Columned(
    private val columnWidth:Int = 20,
    private val separator:String = "|"

){
    fun toColumnLines(column:Int,msg:String): List<String> = msg.chunked(20).map { columnPrefix(column) +it }

    fun columnPrefix(column:Int):String = (0..column* columnWidth).fold(""){ a, b ->  a+ " "}

}