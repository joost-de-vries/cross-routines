import colour.coroutine.columns.ColumnedCoroutine
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.*

private val col = ColumnedCoroutine()
private suspend fun prln(msg: String) = col.prln(msg)


fun main() = runBlocking{

    val channel = Channel<Int>()
    launch {
        for (x in 1..5) {
            val v = x*x
            prln("sending $v")
            channel.send(v)
        }
        channel.close()
    }

    for (y in channel){
        prln("receiving $y")
    }
    prln("Done!")
}