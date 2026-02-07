import java.time.Year
//базовий інтерфейс зброї
interface IWeapon {
    fun getDescription(): String
    fun getPrice(): Double       // Повна ціна (база + обвіс)
    fun getBasePrice(): Double   // Чиста ціна зброї (для амортизації)
    fun getWeight(): Double
    fun getMaxWeight(): Double
    fun getYear(): Int
    fun setPrice(newPrice: Double)
}

abstract class BaseWeapon(
    var name: String,
    var _basePrice: Double, 
    var _weight: Double,   
    var _maxLoad: Double,
    var manufactureYear: Int,
    val range: Int, 
    val damage: Int 
) : IWeapon {
    override fun getDescription() = "$name ($manufactureYear р.в.)"
    
     override fun getPrice() = _basePrice 
    
    override fun getBasePrice() = _basePrice 
    
    override fun getWeight() = _weight
    override fun getMaxWeight() = _maxLoad
    
    override fun getYear() = manufactureYear
    override fun setPrice(newPrice: Double) { this._basePrice = newPrice }
}

class AssaultRifle(year: Int) : BaseWeapon("AK-74", 20000.0, 3.3, 4.5, year, 1000, 70)
class Pistol(year: Int) : BaseWeapon("Glock-17", 15000.0, 0.9, 1.5, year, 50, 30)
class SniperRifle(year: Int) : BaseWeapon("Barrett M82", 80000.0, 14.0, 16.0, year, 1800, 100)
// base decorator
abstract class WeaponDecorator(val weapon: IWeapon) : IWeapon {
    override fun getDescription() = weapon.getDescription()
    override fun getPrice() = weapon.getPrice()
    
  
    override fun getBasePrice() = weapon.getBasePrice()
    
    override fun getWeight() = weapon.getWeight()
    override fun getMaxWeight() = weapon.getMaxWeight() 
    override fun getYear() = weapon.getYear()
    override fun setPrice(newPrice: Double) = weapon.setPrice(newPrice)
}
// приціл
class Scope(weapon: IWeapon) : WeaponDecorator(weapon) {
    val accessoryPrice = 5000.0
    val accessoryWeight = 0.6

    override fun getDescription() = super.getDescription() + " + Оптика"
    override fun getPrice() = super.getPrice() + accessoryPrice
    override fun getWeight() = super.getWeight() + accessoryWeight
}

// глушник
class Silencer(weapon: IWeapon) : WeaponDecorator(weapon) {
    val accessoryPrice = 3000.0
    val accessoryWeight = 0.4

    override fun getDescription() = super.getDescription() + " + Глушник"
    override fun getPrice() = super.getPrice() + accessoryPrice
    override fun getWeight() = super.getWeight() + accessoryWeight
}

// прилад нічного бачення
class NightVision(weapon: IWeapon) : WeaponDecorator(weapon) {
    val accessoryPrice = 12000.0
    val accessoryWeight = 0.8

    override fun getDescription() = super.getDescription() + " + ПНБ"
    override fun getPrice() = super.getPrice() + accessoryPrice
    override fun getWeight() = super.getWeight() + accessoryWeight
}

// FACADE 
class WarehouseFacade {
    private val inventory = mutableListOf<IWeapon>()

    fun addBaseWeapon(type: String, year: Int) {
        val weapon = when (type) {
            "AK" -> AssaultRifle(year)
            "Pistol" -> Pistol(year)
            "Sniper" -> SniperRifle(year)
            else -> throw IllegalArgumentException("Невідомий тип зброї")
        }
        inventory.add(weapon)
        println("На склад додано: ${weapon.getDescription()}")
    }

    fun addAccessoryToLastWeapon(accessoryType: String) {
        if (inventory.isEmpty()) return

        val currentWeapon = inventory.removeAt(inventory.lastIndex)
        
        val newWeaponState: IWeapon = when (accessoryType) {
            "Scope" -> Scope(currentWeapon)
            "Silencer" -> Silencer(currentWeapon)
            "NightVision" -> NightVision(currentWeapon)
            else -> currentWeapon
        }

        // ПЕРЕВІРКА ВАГИ
        if (newWeaponState.getWeight() > newWeaponState.getMaxWeight()) {
            println("ПОМИЛКА: Неможливо встановити $accessoryType на ${currentWeapon.getDescription()}. Перевищення ваги! (${String.format("%.2f", newWeaponState.getWeight())} > ${newWeaponState.getMaxWeight()})")
            inventory.add(currentWeapon)
        } else {
            inventory.add(newWeaponState)
            println("Успішно встановлено $accessoryType. Нова вага: ${String.format("%.2f", newWeaponState.getWeight())} кг")
        }
    }

    fun findMostExpensiveKit() {
        val mostExpensive = inventory.maxByOrNull { it.getPrice() }
        if (mostExpensive != null) {
            println("\n--- Найдорожчий лот в арсеналі ---")
            println("Опис: ${mostExpensive.getDescription()}")
            println("Повна вартість: ${String.format("%.2f", mostExpensive.getPrice())} грн")
        }
    }

    fun applyDepreciation() {
        val currentYear = Year.now().value
        println("\n--- Проводиться переоцінка зносу ---")
        
        for (item in inventory) {
            val age = currentYear - item.getYear()
            if (age > 0) {
                // -2% за кожен рік
                val depreciationFactor = 1.0 - (age * 0.02)
                
                // 1. Беремо тільки базову ціну (без аксесуарів)
                val basePrice = item.getBasePrice()
                val newBasePrice = basePrice * depreciationFactor
                item.setPrice(newBasePrice)
                
                println("${item.getDescription()}: ціна бази знижена на ${(age*2)}% ($age р.)")
            }
        }
    }

    fun showInventory() {
        println("\n--- Поточний склад ---")
        inventory.forEach { 
            println("${it.getDescription()} | Вага: ${String.format("%.2f", it.getWeight())} кг | Ціна: ${String.format("%.2f", it.getPrice())} грн")
        }
    }
}

fun main() {
    val warehouse = WarehouseFacade()

    println("1. Формування першого комплекту (Автомат)")
    warehouse.addBaseWeapon("AK", 2020) 
    warehouse.addAccessoryToLastWeapon("Scope")    
    warehouse.addAccessoryToLastWeapon("Silencer") 
    
    println("\n2. Спроба перевантажити автомат")
    warehouse.addAccessoryToLastWeapon("NightVision") 

    println("\n3. Формування другого комплекту (Пістолет)")
    warehouse.addBaseWeapon("Pistol", 2010) 
    warehouse.addAccessoryToLastWeapon("Silencer") 
    warehouse.addAccessoryToLastWeapon("Scope")

    warehouse.showInventory()

    warehouse.findMostExpensiveKit()

    warehouse.applyDepreciation()
    
    warehouse.showInventory()
}