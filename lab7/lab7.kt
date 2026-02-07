interface Command {
    fun execute()
    fun undo()
}

class ChessGame {
    fun makeMove(from: String, to: String) {
        println("Гра: Фігура переміщена з $from на $to")
    }

    fun rollbackMove(from: String, to: String) {
        println("Гра: Хід скасовано. Фігура повернута з $to назад на $from")
    }

    fun startNewGame() {
        println("\n=== Нова гра розпочата! Дошка розставлена. ===\n")
    }
}

class MoveCommand(
    private val game: ChessGame,
    private val from: String,
    private val to: String
) : Command {
    
    override fun execute() {
        game.makeMove(from, to)
    }

    override fun undo() {
        game.rollbackMove(from, to)
    }
}

class GameHistory {
    private val history = mutableListOf<Command>()

    fun executeCommand(command: Command) {
        command.execute()
        history.add(command)
    }

    fun undoLastMove() {
        if (history.isNotEmpty()) {
            val lastCommand = history.removeAt(history.size - 1)
            lastCommand.undo()
        } else {
            println("Немає ходів для скасування!")
        }
    }
}

fun main() {
    val game = ChessGame()
    val history = GameHistory()

    game.startNewGame()

    println("--- Гравець робить ходи ---")
    val move1 = MoveCommand(game, "E2", "E4")
    history.executeCommand(move1)

    val move2 = MoveCommand(game, "E7", "E5")
    history.executeCommand(move2)

    val move3 = MoveCommand(game, "G1", "F3")
    history.executeCommand(move3)

    println("\n--- Скасування останніх дій ---")
    history.undoLastMove() // Скасує G1 -> F3
    history.undoLastMove() // Скасує E7 -> E5
    
    println("\n--- Поточний стан відновлено ---")
}