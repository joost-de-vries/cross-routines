package colour

enum class Colour(val index:Int){
    RED(0),
    BLUE(1),
    GREEN(2),
    YELLOW(3),
    PURPLE(4)
}
 val orderToColour: Map<Int,Colour> by lazy{ Colour.values().map { it.index to it }.toMap() }

fun println(c:Colour,msg: String) = when(c){
    Colour.RED -> red(msg)
    Colour.BLUE -> blue(msg)
    Colour.GREEN -> green(msg)
    Colour.YELLOW -> yellow(msg)
    Colour.PURPLE -> purple(msg)
}

expect fun red(msg:String):Unit
expect fun blue(msg:String):Unit
expect fun green(msg:String):Unit
expect fun yellow(msg:String):Unit
expect fun purple(msg:String):Unit


