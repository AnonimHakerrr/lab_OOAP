
interface IEmployeeDataSource {
    fun addEmployee(name: String, position: String)
    fun getEmployeeInfo(name: String): String
}


class EmployeeDataSource : IEmployeeDataSource {
    
   
    companion object {
        private val _database = mutableMapOf<String, String>()
    }

    override fun addEmployee(name: String, position: String) {
        printMsg("DB: Спроба запису в базу...")
        simulateNetworkDelay()
        _database[name] = position
        printMsg("DB: Запис успішний [$name - $position]")
    }

    override fun getEmployeeInfo(name: String): String {
        printMsg("DB: Пошук у фізичній базі даних...")
        simulateNetworkDelay() 
        
        return _database[name] ?: "Співробітника не знайдено"
    }
    private fun simulateNetworkDelay() {
        try {
            Thread.sleep(1500) // 1.5 секунди затримки
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }

    private fun printMsg(msg: String) {
        println("\u001B[31m[RealSubject]\u001B[0m $msg") // Червоний колір для БД
    }
}
object DataSourceFactory {
    fun createEmployeeDataSource(): EmployeeDataSource {
        return EmployeeDataSource()
    }
}

class EmployeeDataSourceProxy : IEmployeeDataSource {
    
     private val realDataSource: EmployeeDataSource = DataSourceFactory.createEmployeeDataSource()
    
     private val cache = mutableMapOf<String, String>()

    override fun addEmployee(name: String, position: String) {
        printMsg("Отримано запит на додавання: $name")
         realDataSource.addEmployee(name, position)
       
    }

    override fun getEmployeeInfo(name: String): String {
        printMsg("Запит даних про: $name")

         if (cache.containsKey(name)) {
            printMsg("Знайдено в КЕШІ! Економія часу.")
            return cache[name]!!
        }

        val data = realDataSource.getEmployeeInfo(name)
        
         if (data != "Співробітника не знайдено") {
            cache[name] = data
        }
        
        return data
    }

    private fun printMsg(msg: String) {
        println("\u001B[32m[Proxy]\u001B[0m       $msg") // Зелений колір для Проксі
    }
}

fun main() {
    println("=== СИСТЕМА ОБЛІКУ СПІВРОБІТНИКІВ (PROXY DEMO) ===\n")

    val dataSource: IEmployeeDataSource = EmployeeDataSourceProxy()

    println("--- Крок 1: Додавання даних ---")
    dataSource.addEmployee("Олександр", "Senior Developer")
    dataSource.addEmployee("Марія", "HR Manager")

    println("\n--- Крок 2: Перше читання (Холодний старт) ---")
   val info1 = dataSource.getEmployeeInfo("Олександр")
    println("Результат: $info1")

    println("\n--- Крок 3: Повторне читання (Гарячий кеш) ---")
    val info2 = dataSource.getEmployeeInfo("Олександр")
    println("Результат: $info2")

    println("\n--- Крок 4: Читання іншого співробітника ---")
    val info3 = dataSource.getEmployeeInfo("Марія")
    println("Результат: $info3")
}