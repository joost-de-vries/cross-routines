import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.selects.*
import java.text.SimpleDateFormat
import java.util.*

//from https://github.com/elizarov/DeadlocksInCSP and
// https://medium.com/@elizarov/deadlocks-in-non-hierarchical-csp-e5910d137cc

private const val N_WORKERS = 4

private fun CoroutineScope.downloader(
    references: ReceiveChannel<Reference>,
    locations: SendChannel<Location>,
    contents: ReceiveChannel<LocContent>
) = launch {
    val requested = mutableMapOf<Location, MutableList<Reference>>()
    while (true) {
        select<Unit> {
            references.onReceive { ref ->
                val loc = ref.resolveLocation()
                val refs = requested[loc]
                if (refs == null) {
                    requested[loc] = mutableListOf(ref)
                    locations.send(loc)
                } else {
                    refs.add(ref)
                }
            }
            contents.onReceive { (loc, content) ->
                val refs = requested.remove(loc)!!
                for (ref in refs) {
                    processContent(ref, content)
                }
            }
        }
    }
}

private fun CoroutineScope.worker(
    locations: ReceiveChannel<Location>,
    contents: SendChannel<LocContent>
) = launch {
    for (loc in locations) {
        val content = downloadContent(loc)
        contents.send(LocContent(loc, content))
    }
}

private fun CoroutineScope.processReferences(
    references: ReceiveChannel<Reference>
) {
    val locations = Channel<Location>()
    //val contents = Channel<LocContent>()
    val contents = Channel<LocContent>(Channel.UNLIMITED)
    repeat(N_WORKERS) { worker(locations, contents) }
    downloader(references, locations, contents)
}

fun main() = runBlocking {
    withTimeout(3000) {
        val references = Channel<Reference>()
        processReferences(references)
        var index = 1
        while (true) {
            references.send(Reference(index++))
        }
    }
}

// domain

data class Reference(val index: Int)
data class Location(val index: Int)
data class Content(val index: Int)
data class LocContent(val loc: Location, val content: Content)

fun Reference.resolveLocation(): Location {
    log("Resolving location for $this")
    return Location(index)
}

suspend fun downloadContent(loc: Location): Content {
    log("Downloading $loc")
    delay(10)
    return Content(loc.index)
}

fun processContent(ref: Reference, content: Content) {
    log("Processing $ref $content")
}

private fun log(msg: String) {
    val time = SimpleDateFormat("HH:mm:ss.sss").format(Date())
    println("$time [${Thread.currentThread().name}] $msg")
}
