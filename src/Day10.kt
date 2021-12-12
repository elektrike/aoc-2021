import kotlin.collections.ArrayDeque

enum class SyntaxError { Invalid, Incomplete }

fun main() {
    val legalPairs = mapOf('(' to ')', '[' to ']', '{' to '}', '<' to '>')
    val points = mapOf(
        '(' to 1L, '[' to 2L, '{' to 3L, '<' to 4L,
        ')' to 3L, ']' to 57L, '}' to 1197L, '>' to 25137L
    )

    fun String.checkSyntax(): Pair<SyntaxError, Any> {
        val chars = ArrayDeque<Char>()

        forEach {
            when (it) {
                in legalPairs.keys -> chars.addLast(it)
                legalPairs[chars.last()] -> chars.removeLast()
                else -> return SyntaxError.Invalid to it
            }
        }

        return SyntaxError.Incomplete to chars.reversed()
    }

    fun part1(input: List<String>): Long =
        input
            .map { it.checkSyntax() }
            .filter { it.first == SyntaxError.Invalid }
            .sumOf { points[it.second] ?: 0 }

    fun part2(input: List<String>): Long {
        val scores = input
            .map { it.checkSyntax() }
            .filter { it.first == SyntaxError.Incomplete }
            .map {
                val chars = it.second as List<*>
                chars
                    .map { char -> points[char] ?: 0 }
                    .reduce { res, points -> res * 5 + points }
            }
            .sorted()

        return scores[scores.size / 2]
    }
}
