fun main() {
    data class Point(val x: Int, val y: Int, val height: Int)
    data class Area(val point: Point, val left: Point?, val right: Point?, val top: Point?, val bottom: Point?)

    fun List<String>.getPoints() =
        flatMapIndexed { rowIndex, row ->
            row.map { it.toString().toInt() }
               .mapIndexed { pointIndex, height -> Point(pointIndex, rowIndex, height) }
        }

    fun List<Point>.getAreas() =
        map { point ->
            Area(
                point,
                singleOrNull { it.x == point.x - 1 && it.y == point.y },
                singleOrNull { it.x == point.x + 1 && it.y == point.y },
                singleOrNull { it.x == point.x && it.y == point.y - 1 },
                singleOrNull { it.x == point.x && it.y == point.y + 1 }
            )
        }

    fun Point.isLowerThan(point: Point?) = point?.let { height < point.height } ?: true

    fun Area.isLowest() =
        point.isLowerThan(left) &&
        point.isLowerThan(right) &&
        point.isLowerThan(top) &&
        point.isLowerThan(bottom)

    fun List<Area>.getLowpoints() = filter { it.isLowest() }.map { it.point }

    fun Point.isBasinPoint(nextHeight: Int) = let { height in (nextHeight + 1) until 9 }

    fun Area.getBasinPoints(): List<Point> {
        val basinPoints = mutableListOf<Point>()

        if (top?.isBasinPoint(point.height) == true)
            basinPoints.add(top)
        if (bottom?.isBasinPoint(point.height) == true)
            basinPoints.add(bottom)
        if (left?.isBasinPoint(point.height) == true)
            basinPoints.add(left)
        if (right?.isBasinPoint(point.height) == true)
            basinPoints.add(right)

        return basinPoints
    }

    fun List<Area>.getBasins(): List<Set<Point>> {
        fun getBasin(point: Point): List<Point> {
            val basinPoints = single { it.point == point }.getBasinPoints()

            return basinPoints.plus(basinPoints.flatMap { getBasin(it) }).plus(point)
        }

        return getLowpoints().map { getBasin(it).toSet() }
    }

    fun part1(input: List<String>): Int =
        input
            .getPoints()
            .getAreas()
            .getLowpoints()
            .sumOf { it.height + 1 }

    fun part2(input: List<String>): Int =
        input
            .getPoints()
            .getAreas()
            .getBasins()
            .map { it.size }
            .sorted()
            .takeLast(3)
            .reduce { res, basinSize -> res * basinSize }
}
