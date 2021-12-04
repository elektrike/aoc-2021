fun main() {
    data class Number(val value: Int, var marked: Boolean = false) {
        fun mark() { marked = true }
    }

    data class Row(val numbers: List<Number>) {
        fun markNumber(drawnNumber: Int) = numbers.find { it.value == drawnNumber }?.mark()
        fun isComplete() = numbers.all { it.marked }
        fun score(): Int = numbers.filter { !it.marked }.sumOf { it.value }
    }

    fun String.toRow(): Row = Row(split(" ").filter { it.isNotBlank() }.map { Number(it.toInt()) })

    fun List<Row>.toColumns(): List<Row> {
        val columns = toMutableList()
        for (i in 0 until this.size) {
            val numbers = mutableListOf<Number>()
            for (j in 0 until this.size) {
                numbers.add(this[j].numbers[i])
            }
            columns.add(Row(numbers))
        }

        return columns
    }

    data class Ticket(val rows: List<Row>, var scored: Boolean = false) {
        fun markNumber(drawnNumber: Int) = rows.forEach { it.markNumber(drawnNumber) }
        fun completeRow(): Row? = rows.firstOrNull { it.isComplete() }
        fun completeColumn(): Row? = rows.toColumns().firstOrNull { it.isComplete() }
        fun score(drawnNumber: Int): Int {
            completeRow()?.let { return (rows.sumOf { it.score() }) * drawnNumber }
            completeColumn()?.let { return (rows.sumOf { it.score() }) * drawnNumber }

            return 0
        }
    }

    fun List<Ticket>.allScored(): Boolean = all { ticket -> ticket.scored }

    fun readNumbers(input: String): List<Int> = input.split(",").map { it.toInt() }

    fun getTickets(input: List<String>): List<Ticket> =
        input.asSequence()
            .drop(1)
            .map { it.toRow() }
            .chunked(5)
            .map { Ticket(it) }
            .toList()

    fun part1(input: List<String>): Int {
        val tickets = getTickets(input)
        readNumbers(input.first()).forEach { drawnNumber ->
            tickets.forEach { it.markNumber(drawnNumber) }
            tickets.map { it.score(drawnNumber) }.firstOrNull { it != 0 }?.let { return it }
        }

        return 0
    }

    fun part2(input: List<String>): Int {
        val tickets = getTickets(input)
        readNumbers(input.first()).forEach { drawnNumber ->
            tickets.filter { !it.scored }.forEach {
                it.markNumber(drawnNumber)
                val score = it.score(drawnNumber)
                if (score > 0) it.scored = true
                if (tickets.allScored()) return score
            }
        }

        return 0
    }
}
