fun main() {
    data class Point(val x: Int, val y: Int)

    data class Line(val from: Point, val to: Point) {
        fun isVertical(): Boolean = from.x == to.x
        fun isHorizontal(): Boolean = from.y == to.y
        fun isDiagonal(): Boolean = !isHorizontal() && !isVertical()
    }

    fun String.toPoint(): Point {
        val xy = split(",").map { it.toInt() }
        return Point(xy.first(), xy.last())
    }

    fun List<String>.toLines(): List<Line> =
        map { it.split(" -> ") }
            .map { Line(it.first().toPoint(), it.last().toPoint()) }

    fun collectRange(from: Int, to: Int): List<Int> =
        if (from < to)
            (from..to).toList()
        else
            (from downTo to).toList()

    fun drawLines(lines: List<Line>): List<Point> =
        lines.flatMap {
            val xs = collectRange(it.from.x, it.to.x)
            val ys = collectRange(it.from.y, it.to.y)

            if (it.isVertical())
                ys.map { y -> Point(xs.first(), y) }.toList()
            else if (it.isHorizontal())
                xs.map { x -> Point(x, ys.first()) }.toList()
            else
                List(xs.size) { i -> Point(xs[i], ys[i]) }
        }

    fun countOverlappingPoints(lines: List<Line>): Int =
        drawLines(lines)
            .groupingBy { it }
            .eachCount()
            .filter { it.value > 1 }
            .size

    fun part1(input: List<String>): Int = countOverlappingPoints(input.toLines().filter { !it.isDiagonal() })
    fun part2(input: List<String>): Int = countOverlappingPoints(input.toLines())
}
