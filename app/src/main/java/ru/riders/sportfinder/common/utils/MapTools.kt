package ru.riders.sportfinder.common.utils

import com.yandex.mapkit.geometry.Geo
import com.yandex.mapkit.geometry.Point
import kotlin.math.abs

object MapTools {
    private const val pointEps = 1e-4

    fun calcDistance(points: List<Point>): Double {
        var dist = 0.0
        for (i in 1 until points.size) {
            dist += Geo.distance(points[i-1], points[i])
        }
        return dist
    }

    fun findPointInList(
        currPoint: Point,
        listOfPoints: List<Point>
    ): List<Point> {
        val answer = mutableListOf<Point>()
        listOfPoints.forEach { point ->
            if (abs(currPoint.latitude - point.latitude) < pointEps &&
                abs(currPoint.longitude - point.longitude) < pointEps) {
                answer.add(point)
            }
        }
        return answer
    }

    fun getAveragePoint(points: List<Point>): Point {
        val averageLatitude = points.sumOf { it.latitude } / points.size
        val averageLongitude = points.sumOf { it.longitude } / points.size

        return Point(averageLatitude, averageLongitude)
    }
}