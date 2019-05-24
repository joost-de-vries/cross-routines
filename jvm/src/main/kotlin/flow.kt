import colour.coroutine.columns.ColumnedCoroutine
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
private val col = ColumnedCoroutine()
private suspend fun prln(msg: String) = col.prln(msg)


@FlowPreview
val ints: Flow<Int> = flow {
    prln("starting")
    for (i in 1..10) {
        delay(100)
        emit(i)
    }
}

 fun main()= runBlocking {
    prln("1")
    ints.map { it * 2 }
        .collect { prln(it.toString()) }

    prln("2")
    ints.map { it * 2 }
        .collect { prln(it.toString()) }
}