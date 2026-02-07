
interface IPaymentSystem {
    fun calculateSalary(rate: Double, volume: Double): Double
    fun getPaymentType(): String
}

class HourlyPayment : IPaymentSystem {
    override fun calculateSalary(rate: Double, volume: Double): Double {
        return rate * volume
    }

    override fun getPaymentType() = "Погодинна (за час)"
}

class PieceworkPayment : IPaymentSystem {
    override fun calculateSalary(rate: Double, volume: Double): Double {
        return rate * volume
    }

    override fun getPaymentType() = "Відрядна (за результат)"
}

abstract class Employee(
    val name: String, 
    val position: String, 
    protected var paymentSystem: IPaymentSystem // <--- МІСТ
) {
  
    fun setPaymentMethod(newMethod: IPaymentSystem) {
        this.paymentSystem = newMethod
    }

    abstract fun processSalary(rate: Double, volume: Double)
}

class Developer(name: String, paymentSystem: IPaymentSystem) : Employee(name, "Розробник", paymentSystem) {
    override fun processSalary(rate: Double, volume: Double) {
        val total = paymentSystem.calculateSalary(rate, volume)
        println("------------------------------------------------")
        println("Працівник: $name | Посада: $position")
        println("Тип нарахування: ${paymentSystem.getPaymentType()}")
        // Логіка специфічна для розробника (наприклад, бонуси, податки IT)
        println("Розрахунок: $rate * $volume = $total грн")
    }
}

class Manager(name: String, paymentSystem: IPaymentSystem) : Employee(name, "Менеджер", paymentSystem) {
    override fun processSalary(rate: Double, volume: Double) {
        val total = paymentSystem.calculateSalary(rate, volume)
        println("------------------------------------------------")
        println("Працівник: $name | Посада: $position")
        println("Тип нарахування: ${paymentSystem.getPaymentType()}")
        println("До виплати: $total грн")
    }
}

fun main() {
    println("=== БУХГАЛТЕРІЯ: НАРАХУВАННЯ ЗАРПЛАТИ ===\n")

    val hourly = HourlyPayment()
    val piecework = PieceworkPayment()

    val devIvan = Developer("Іван Петренко", hourly)
    
     val managerOlena = Manager("Олена Сидорова", piecework)

      devIvan.processSalary(rate = 300.0, volume = 160.0)

    managerOlena.processSalary(rate = 2000.0, volume = 10.0)

    println("\n=== ЗМІНА УМОВ ПРАЦІ (DEMO BRIDGE) ===")
       
    println(">> Івана перевели на проектну роботу (відрядна оплата)")
    devIvan.setPaymentMethod(piecework)
    
     devIvan.processSalary(rate = 15000.0, volume = 1.0)
}