fun main() {
    fun entries(input: List<String>) =
        input.map { entry ->
            entry.split(" | ")
                .map {
                    it.split(" ")
                        .map { signal -> signal.toSet() }
                }
        }

    fun part1(input: List<String>): Int =
        entries(input).sumOf { (_, output) ->
            output.count { it.size in listOf(2, 3, 4, 7) }
        }

    fun part2(input: List<String>): Int =
        entries(input)
            .sumOf { (signalPatterns, output) ->
                val one = signalPatterns.single { it.size == 2 }
                val four = signalPatterns.single { it.size == 4 }

                output
                    .joinToString("") {
                        when (it.size) {
                            2 -> "1"
                            3 -> "7"
                            4 -> "4"
                            5 ->
                                if (it.intersect(one).size == 2)
                                    "3"
                                else if (it.intersect(four).size == 2)
                                    "2"
                                else
                                    "5"
                            6 ->
                                if (it.intersect(one).size == 1)
                                    "6"
                                else if (it.intersect(four).size == 3)
                                    "0"
                                else
                                    "9"
                            else -> "8"
                        }
                    }
                    .toInt()
            }
}
