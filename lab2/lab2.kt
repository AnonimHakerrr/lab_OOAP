
abstract class JewelryItem(val name: String, val weight: Double, val workCost: Double) {
    abstract val pricePerGram: Double
    abstract val metalType: String

    fun calculateTotalPrice(): Double {
        return (weight * pricePerGram) + workCost
    }

    fun getInfo() {
        println("Виріб: $name | Метал: $metalType | Вага: ${weight}г | Ціна: ${calculateTotalPrice()} грн")
    }
}

class GoldEarrings(weight: Double, workCost: Double) : JewelryItem("Сережки", weight, workCost) {
    override val metalType = "Золото 585"
    override val pricePerGram = 2500.0
}

class GoldRing(weight: Double, workCost: Double) : JewelryItem("Каблучка", weight, workCost) {
    override val metalType = "Золото 585"
    override val pricePerGram = 2000.0
}
class GoldChain(weight: Double, workCost: Double) : JewelryItem("Ланцюжок", weight, workCost) {
    override val metalType = "Золото 585"
    override val pricePerGram = 2200.0
}

class SilverEarrings(weight: Double, workCost: Double) : JewelryItem("Сережки", weight, workCost) {
    override val metalType = "Срібло 925"
    override val pricePerGram = 100.0
}

class SilverRing(weight: Double, workCost: Double) : JewelryItem("Каблучка", weight, workCost) {
    override val metalType = "Срібло 925"
    override val pricePerGram = 100.0
}
class SilverChain(weight: Double, workCost: Double) : JewelryItem("Ланцюжок", weight, workCost) {
    override val metalType = "Срібло 925"
    override val pricePerGram = 120.0
}

interface IJewelryFactory {
    fun createEarrings(weight: Double, workCost: Double): JewelryItem
    fun createRing(weight: Double, workCost: Double): JewelryItem
    fun createChain(weight: Double, workCost: Double): JewelryItem
}

class GoldFactory : IJewelryFactory {
    override fun createEarrings(weight: Double, workCost: Double): JewelryItem {
        return GoldEarrings(weight, workCost)
    }

    override fun createRing(weight: Double, workCost: Double): JewelryItem {
        return GoldRing(weight, workCost)
    }
    override fun createChain(weight: Double, workCost: Double): JewelryItem {
        return GoldChain(weight, workCost)
    }
}

class SilverFactory : IJewelryFactory {
    override fun createEarrings(weight: Double, workCost: Double): JewelryItem {
        return SilverEarrings(weight, workCost)
    }

    override fun createRing(weight: Double, workCost: Double): JewelryItem {
        return SilverRing(weight, workCost)
    }
    override fun createChain(weight: Double, workCost: Double): JewelryItem {
        return SilverChain(weight, workCost)
    }
}

fun main() {
    println("=== Ювелірний Каталог ===")

    val goldFactory: IJewelryFactory = GoldFactory()
    val silverFactory: IJewelryFactory = SilverFactory()

    val myGoldEarrings = goldFactory.createEarrings(3.0, 1500.0)
    val mySilverEarrings = silverFactory.createEarrings(3.0, 1500.0)

    val myGoldRing = goldFactory.createRing(2.0, 800.0)
    val mySilverChain = silverFactory.createChain(5.0, 2000.0)

    println("\n--- Категорія: ЗОЛОТО ---")
    myGoldEarrings.getInfo()
    myGoldRing.getInfo()

    println("\n--- Категорія: СРІБЛО ---")
    mySilverEarrings.getInfo()
    mySilverChain.getInfo()
}