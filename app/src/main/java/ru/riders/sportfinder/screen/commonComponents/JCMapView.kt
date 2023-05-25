package ru.riders.sportfinder.screen.commonComponents

import android.content.Context
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.InputListener
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import ru.riders.sportfinder.R
import java.lang.Math.abs

class JCMapView(
    context: Context,
    private val onMapTapImpl: (map: Map, point: Point) -> Unit,
    private val onMapLongTapImpl: (map: Map, point: Point) -> Unit
) : MapView(context), InputListener {

    val points = mutableMapOf<Point, PlacemarkMapObject>()

    init {
        this.map.addInputListener(this)
    }
    override fun onMapTap(p0: Map, p1: Point) {
        onMapTapImpl(p0, p1)
    }

    override fun onMapLongTap(p0: Map, p1: Point) {
        onMapLongTapImpl(p0, p1)
    }

    fun addPoint(point: Point) {
        points[point] = map.mapObjects.addPlacemark(point).apply {
            setIcon(ImageProvider.fromBitmap(ContextCompat.getDrawable(context, R.drawable.ic_location_white_24)!!.toBitmap()))
            addTapListener { _, _ -> true }
        }
    }
    fun removePoint(point: Point) {
        for ((placePoint, placeObject) in points) {
            if (abs(point.latitude - placePoint.latitude) < 1e-3 && abs(point.longitude - placePoint.longitude) < 1e-3) {
                map.mapObjects.remove(placeObject)
                return
            }
        }
    }
}
