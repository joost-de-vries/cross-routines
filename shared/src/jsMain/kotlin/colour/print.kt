package colour

private val RESET = "\u001B[0m"
private val BLACK = "\u001B[30m"
private val RED = "color:red"
private val GREEN = "color:green"
private val YELLOW = "color:yellow"
private val BLUE = "color:blue"
private val PURPLE = "color:purple"
private val CYAN = "color:cyan"
private val WHITE = "color:white"

actual fun red(msg: String): Unit = printColour(RED, msg)
actual fun blue(msg: String): Unit = printColour(BLUE, msg)
actual fun green(msg: String): Unit = printColour(GREEN, msg)
actual fun yellow(msg: String): Unit = printColour(YELLOW, msg)
actual fun purple(msg: String): Unit = printColour(PURPLE, msg)


private fun printColour(colour: String, msg: String): Unit = console.log(msg, colour)