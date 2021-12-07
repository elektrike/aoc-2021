import kotlin.math.abs

fun main() {
    fun List<String>.readPositions(): List<Int> = first().split(",").map { it.toInt() }

    fun List<Int>.median() = sorted().let { (it[it.size / 2] + it[(it.size - 1) / 2]) / 2 }

    fun part1(input: List<String>): Int {
        val crabPositions = input.readPositions()
        val bestPosition = crabPositions.median()

        return crabPositions.sumOf { abs(it - bestPosition) }
    }

    fun part2(input: List<String>): Int {
        val crabPositions = input.readPositions()
        val bestPosition = crabPositions.average().toInt()

        return crabPositions.sumOf { (0..abs(it - bestPosition)).sum() }
    }
}
