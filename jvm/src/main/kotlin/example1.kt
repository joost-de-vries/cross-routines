import colour.*
import colour.coroutine.columns.Columned
import colour.coroutine.columns.ColumnedCoroutine
import kotlinx.coroutines.*
import kotlin.coroutines.EmptyCoroutineContext

private val col = ColumnedCoroutine()
private suspend fun prln(msg: String) = col.prln(msg)

fun main() {
    // on an OS thread

    // start root scope that will block & wait for all children
    runBlocking { // this: CoroutineScope

        launch { // this is root scope above

            delay(200L)
            prln("end co routine ${conc()}")
        }

        coroutineScope {
            launch {
                delay(500L)
                prln("end other coroutine ${conc()}")
            }

            delay(100L)
            prln("end sub coroutine scope") // This line will be printed before the end of nested launch
        }

        prln("end root coroutinescope") // This line is not printed until the nested launch completes
    }
}

private fun CoroutineScope.conc(): String = "${thread()} ${job()}"

private fun thread(): String = "T "+Thread.currentThread().name

private fun CoroutineScope.job(): String {
    val job = coroutineContext[Job]!!
    val c = job.children.joinToString().run { if (isEmpty()) this else "C $this" }
    return "J ${job.hashCode()} $c"
}

