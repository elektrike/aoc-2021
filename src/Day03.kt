fun main() {
    fun List<String>.countBits(index: Int): Map<Char, Int> = groupingBy { it.toCharArray()[index] }.eachCount()

    val comparator: Comparator<Map.Entry<Char, Int>> = compareBy({ it.value }, { it.key })

    fun Map<Char, Int>.moreCommonBit(): Char = maxWithOrNull(comparator)?.key ?: '1'

    fun Map<Char, Int>.lessCommonBit(): Char = minWithOrNull(comparator)?.key ?: '0'

    fun getGammaRate(report: List<String>): String =
        (0 until report.first().length)
            .map { report.countBits(it).moreCommonBit() }
            .let { String(it.toCharArray()) }

    fun part1(input: List<String>): Int {
        val gammaRate = getGammaRate(input)
        val epsilonRate = String(gammaRate.map { if (it == '0') '1' else '0' }.toCharArray())

        return gammaRate.toInt(2) * epsilonRate.toInt(2)
    }

    fun part2(input: List<String>): Int {
        val (o2Rating, co2Rating) = listOf(input.toMutableList(), input.toMutableList())

        for (i in 0 until input.first().length) {
            val moreCommonBit = o2Rating.countBits(i).moreCommonBit()
            o2Rating.retainAll { it[i] == moreCommonBit }

            val lessCommonBit = co2Rating.countBits(i).lessCommonBit()
            co2Rating.retainAll { it[i] == lessCommonBit }
        }

        return o2Rating.single().toInt(2) * co2Rating.single().toInt(2)
    }
}
