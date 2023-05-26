package ru.riders.sportfinder.screen.common_components

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
    context: Context
) : MapView(context), InputListener {

    private val points = mutableMapOf<Point, PlacemarkMapObject>()
    val onMapTapAction: ((Map, Point) -> Unit)? = null
    val onMapLongTapAction: ((Map, Point) -> Unit)? = null


    init {
        this.map.addInputListener(this)
    }
    override fun onMapTap(p0: Map, p1: Point) {
        onMapTapAction?.invoke(p0, p1)
    }

    override fun onMapLongTap(p0: Map, p1: Point) {
        onMapLongTapAction?.invoke(p0, p1)
    }

    fun addPoint(point: Point) {
        points[point] = map.mapObjects.addPlacemark(point).apply {
            setIcon(ImageProvider.fromBitmap(ContextCompat.getDrawable(context, R.drawable.ic_location_black_24)!!.toBitmap()))
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
