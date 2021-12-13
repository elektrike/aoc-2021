fun main() {
    data class Instruction(val axis: Char, val line: Int)
    data class Dot(var x: Int, var y: Int)

    fun String.isInstruction() = startsWith("fold along")

    fun List<String>.getInstructions() =
        filter { it.isInstruction() }
            .map { it.split(" ").last().split("=") }
            .map { Instruction(it.first().single(), it.last().toInt()) }

    fun List<String>.getPaper() =
        filterNot { it.isInstruction() || it.isEmpty() }
            .map { it.split(",") }
            .map { Dot(it.first().toInt(), it.last().toInt()) }

    fun List<Dot>.fold(instruction: Instruction): List<Dot> {
        val axis = instruction.axis
        val line = instruction.line

        forEach { dot ->
            if (axis == 'y' && dot.y > line)
                this.find { it == dot }?.y = dot.y - (dot.y - line) * 2
            else if (axis == 'x' && dot.x > line)
                this.find { it == dot }?.x = dot.x - (dot.x - line) * 2
        }

        return distinct()
    }

    fun List<Dot>.printCode() {
        for (y in 0..maxOf { it.y }) {
            val paperLine = StringBuilder()
            for (x in 0..maxOf { it.x }) {
                if (any { it.x == x && it.y == y })
                    paperLine.append("#")
                else
                    paperLine.append(".")
            }

            println(paperLine)
        }
    }

    fun part1(input: List<String>): Int =
        input.getInstructions()
            .map { input.getPaper().fold(it) }
            .first()
            .size

    fun part2(input: List<String>) {
        val paper = input.getPaper()

        input.getInstructions()
            .flatMap { paper.fold(it) }
            .printCode()
    }
}
