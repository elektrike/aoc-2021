fun main() {
    fun nextDay(previousCycle: Map<Int, Long>): Map<Int, Long> {
        val cycle = mutableMapOf<Int, Long>()

        previousCycle
            .filter { it.key != 0 }
            .forEach { cycle[it.key - 1] = previousCycle.getOrDefault(it.key, 0) }

        val spawningLanternfish = previousCycle.getOrDefault(0, 0)
        cycle[6] = cycle.getOrDefault(6, 0) + spawningLanternfish
        cycle[8] = spawningLanternfish

        return cycle
    }

    fun String.toTimers(): List<Int> = split(",").map { it.toInt() }

    fun countLanternfish(input: String, days: Int): Long {
        var lanternfish = input.toTimers()
            .groupingBy { it }
            .eachCount()
            .mapValues { it.value.toLong() }

        repeat(days) {
            lanternfish = nextDay(lanternfish)
        }

        return lanternfish
            .map { it.value }
            .sum()
    }

    fun part1(input: List<String>): Long = countLanternfish(input.first(), 80)
    fun part2(input: List<String>): Long = countLanternfish(input.first(), 256)
}
