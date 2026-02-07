data class BrakeState(
    val springsAttached: Boolean,
    val padsPosition: String,
    val cylinderFixed: Boolean
)


class DrumBrake(val side: String) {
    var state = BrakeState(true, "Aligned", true)

    fun disassemble() {
        state = BrakeState(false, "Removed", false)
        println("[$side] Гальма розібрано.")
    }

    fun saveToMemento(): BrakeMemento {
        println("[$side] Стан зафіксовано як зразок.")
        return BrakeMemento(state)
    }

    fun restoreFromMemento(memento: BrakeMemento) {
        this.state = memento.state
        println("[$side] Зібрано по зразку: springs=${state.springsAttached}, pads=${state.padsPosition}")
    }
}

class BrakeMemento(val state: BrakeState)

class Mechanic {
    private var referenceCopy: BrakeMemento? = null

    fun workOnCar(leftBrake: DrumBrake, rightBrake: DrumBrake) {
        println("=== ПОЧАТОК РОБОТИ ===")
        
        referenceCopy = rightBrake.saveToMemento()
        
        println("\n--- Ремонт лівої сторони ---")
        leftBrake.disassemble()
        leftBrake.restoreFromMemento(referenceCopy!!)

        println("\n--- Ремонт правої сторони ---")
        referenceCopy = leftBrake.saveToMemento()
        
        rightBrake.disassemble()
        rightBrake.restoreFromMemento(referenceCopy!!)
        
        println("\n=== РОБОТУ ЗАВЕРШЕНО ===")
    }
}

fun main() {
    val left = DrumBrake("Ліва сторона")
    val right = DrumBrake("Права сторона")
    val mechanic = Mechanic()

    mechanic.workOnCar(left, right)
}