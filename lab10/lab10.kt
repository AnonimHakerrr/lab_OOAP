
data class Document(
    val pages: Int,
    val isColor: Boolean,
    val isLargeFormat: Boolean, 
    val title: String
)

interface PrintStrategy {
    fun print(document: Document)
}

class LaserPrinter : PrintStrategy {
    override fun print(document: Document) {
        println("Лазерний принтер: Швидкий чорно-білий друк ${document.pages} стор. ['${document.title}']")
    }
}

class ColorPrinter : PrintStrategy {
    override fun print(document: Document) {
        println("Кольоровий принтер: Якісний фотодрук ${document.pages} стор. ['${document.title}']")
    }
}

class PlotterPrinter : PrintStrategy {
    override fun print(document: Document) {
        println("Плоттер: Друк широкоформатних креслень для ['${document.title}']")
    }
}

class PrintManager {
    private var strategy: PrintStrategy? = null

    fun processDocument(doc: Document) {
        strategy = when {
            doc.isLargeFormat -> PlotterPrinter()
            doc.isColor -> ColorPrinter()
            else -> LaserPrinter()
        }
        
        strategy?.print(doc)
    }
}
fun main() {
    val manager = PrintManager()

    val docs = listOf(
        Document(10, isColor = false, isLargeFormat = false, "Звіт.pdf"),
        Document(2, isColor = true, isLargeFormat = false, "Фото.jpg"),
        Document(1, isColor = false, isLargeFormat = true, "Креслення_Будинку.dwg")
    )

    println("=== СИСТЕМА ДРУКУ РОЗПОЧАЛА РОБОТУ ===\n")
    docs.forEach { manager.processDocument(it) }
}