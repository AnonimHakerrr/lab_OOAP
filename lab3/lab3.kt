import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.random.Random

enum class ProductCategory(val title: String, val markupPercent: Double) {
    FOOD("Продукти харчування", 0.05), // 5%
    MEDICINE("Ліки", 0.10),            // 10%
    CLOTHING("Одяг", 0.15)             // 15%
}


object SalesCounter {
    var totalItemsSold: Int = 0
        private set 
    
    var totalRevenue: Double = 0.0
        private set

    private val logFile = File("sales_log.txt")

    init {
        logFile.writeText("=== Журнал продажів запущено: ${LocalDateTime.now()} ===\n")
        println("SalesCounter ініціалізовано. Готовий до роботи.")
    }

    fun registerSale(category: ProductCategory) {
        val basePrice = Random.nextDouble(50.0, 500.0)
        val finalPrice = basePrice * (1 + category.markupPercent)
        totalItemsSold++
        totalRevenue += finalPrice
        logTransaction(category.title, finalPrice)
        println("Продано: ${category.title}. Ціна: ${"%.2f".format(finalPrice)} грн.")
    }

    private fun logTransaction(itemName: String, price: Double) {
        val currentDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        val logEntry = "$currentDateTime | Категорія: $itemName | Сума: ${"%.2f".format(price)}\n"
        
        logFile.appendText(logEntry)
    }
    
    fun printReport() {
        println("\n--- ФІНАЛЬНИЙ ЗВІТ ---")
        println("Всього продано товарів: $totalItemsSold шт.")
        println("Загальна виручка: ${"%.2f".format(totalRevenue)} грн")
        println("Детальний лог записано у файл: ${logFile.absolutePath}")
    }
}

fun main() {
    println("Магазин відкрито.\n")
    
    SalesCounter.registerSale(ProductCategory.FOOD)
    
    SalesCounter.registerSale(ProductCategory.MEDICINE)
    
    SalesCounter.registerSale(ProductCategory.CLOTHING)
    
    SalesCounter.registerSale(ProductCategory.FOOD)

    SalesCounter.printReport()
}