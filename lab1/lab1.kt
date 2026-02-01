abstract class Shape {
    init {
        println("Об'єкт створено")
    }
    abstract val area: Double
}

class Square(val side: Double) : Shape() {
    override val area: Double
        get() = side * side
}

class Rectangle(val width: Double, val height: Double) : Shape() {
    override val area: Double
        get() = width * height 
}

class Circle(val radius: Double) : Shape() {
    override val area: Double
        get() = Math.PI * radius * radius
}

fun main() {
    val s = Square(5.0)
    val r = Rectangle(4.0, 5.0)
    val c = Circle(3.0)

    println("--- Результати ---")
    println("Площа квадрата: ${s.area}")
    println("Площа прямокутника: ${r.area}")
    println("Площа круга: ${c.area}")
}