fun main() {
    fun calculateIncreases(input: List<String>, windowSize: Int): Int {
        return input.asSequence()
            .map { it.toInt() }
            .windowed(windowSize, 1)
            .count { it.first() < it.last() }
    }

    fun part1(input: List<String>): Int = calculateIncreases(input, 2)
    fun part2(input: List<String>): Int = calculateIncreases(input, 4)
}
