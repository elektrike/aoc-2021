import java.io.File

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt").readLines()

/**
 * Reads non-blank lines from the given input txt file.
 */
fun readInputTrimmed(name: String) = File("src", "$name.txt").readLines()
    .filter { it.trimIndent().isNotBlank() }