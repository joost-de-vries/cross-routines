package colour


private val RESET = "\u001B[0m"
private val BLACK = "\u001B[30m"
private val RED = "\u001B[31m"
private val GREEN = "\u001B[32m"
private val YELLOW = "\u001B[33m"
private val BLUE = "\u001B[34m"
private val PURPLE = "\u001B[35m"
private val CYAN = "\u001B[36m"
private val WHITE = "\u001B[37m"

actual fun red(msg:String):Unit = printColour(RED,msg)
actual fun blue(msg:String):Unit = printColour(BLUE,msg)
actual fun green(msg:String):Unit = printColour(GREEN,msg)
actual fun yellow(msg:String):Unit = printColour(YELLOW,msg)
actual fun purple(msg:String):Unit = printColour(PURPLE,msg)


private fun printColour(colour:String, msg:String):Unit = println(colour + msg + RESET)