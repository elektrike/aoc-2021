fun main() {
    fun calculateIncreases(input: List<String>, windowSize: Int): Int {
        return input.asSequence()
            .map { it.toInt() }
            .windowed(windowSize, 1)
            .map { if (it.first() < it.last()) 1 else 0 }
            .sum()
    }

    fun part1(input: List<String>): Int = calculateIncreases(input, 2)
    fun part2(input: List<String>): Int = calculateIncreases(input, 4)
}
