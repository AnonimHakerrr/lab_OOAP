
interface Expression {
    fun interpret(): Int
}
class NumberExpression(private val number: Int) : Expression {
    override fun interpret(): Int = number
}
class AddExpression(private val left: Expression, private val right: Expression) : Expression {
    override fun interpret(): Int = left.interpret() + right.interpret()
}

class SubtractExpression(private val left: Expression, private val right: Expression) : Expression {
    override fun interpret(): Int = left.interpret() - right.interpret()
}

class ArithmeticParser {
    fun parse(input: String): Expression {
        val tokens = input.split(" ")
        var currentExpression: Expression = NumberExpression(tokens[0].toInt())

        var i = 1
        while (i < tokens.size) {
            val operator = tokens[i]
            val nextValue = NumberExpression(tokens[i + 1].toInt())

            currentExpression = when (operator) {
                "+" -> AddExpression(currentExpression, nextValue)
                "-" -> SubtractExpression(currentExpression, nextValue)
                else -> throw IllegalArgumentException("Невідомий оператор: $operator")
            }
            i += 2
        }
        return currentExpression
    }
}
fun main() {
    println("=== INTERPRETER PATTERN DEMO (MATH) ===\n")

    val parser = ArithmeticParser()
    
    // ВАЖЛИВО: розділяйте числа та знаки пробілами
    val expressions = listOf(
        "10 + 5",
        "20 - 8",
        "10 + 20 - 5 + 7"
    )

    

    for (str in expressions) {
        try {
            val tree = parser.parse(str)
            val result = tree.interpret()
            println("Вираз: \"$str\" | Результат: $result")
        } catch (e: Exception) {
            println("Помилка при розборі: ${e.message}")
        }
    }
}