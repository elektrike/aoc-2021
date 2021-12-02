fun main() {
    fun parseInstructions(input: List<String>): List<Pair<String, Int>> =
        input.map {
            val (key, value) = it.split(" ")
            Pair(key, Integer.parseInt(value))
        }

    fun part1(instructions: List<Pair<String, Int>>): Int {
        val count = instructions
            .groupBy({ it.first }, { it.second })
            .mapValues { it.value.sum() }

        return (count.getValue("down") - count.getValue("up")) * count.getValue("forward")
    }

    data class State(val position: Int, val depth: Int, val aim: Int) {
        fun addAim(value: Int): State = this.copy(aim = aim + value)
        fun moveSubmarine(value: Int): State = this.copy(position = position + value, depth = depth + aim * value)
    }

    fun followInstruction(instruction: Pair<String, Int>, state: State): State =
        when (instruction.first) {
            "down" -> state.addAim(instruction.second)
            "up" -> state.addAim(-instruction.second)
            "forward" -> state.moveSubmarine(instruction.second)
            else -> state
        }

    fun part2(instructions: List<Pair<String, Int>>): Int {
        fun dive(instructions: List<Pair<String, Int>>, state: State): Int =
            if (instructions.isEmpty())
                state.position * state.depth
            else
                dive(instructions.drop(1), followInstruction(instructions.first(), state))

        return dive(instructions, State(0, 0, 0))
    }
}
