package ru.riders.sportfinder.screen.common_components

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.geometry.Polyline
import com.yandex.mapkit.map.InputListener
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.mapkit.map.PolylineMapObject
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import ru.riders.sportfinder.R
import java.lang.Math.abs

class JCMapView(
    context: Context
) : MapView(context), InputListener {

    private val pointsSportCourts = mutableMapOf<Point, PlacemarkMapObject>()
    private var pointsRunningTrack: Pair<List<Point>, PolylineMapObject>? = null

    val onMapTapAction: ((Map, Point) -> Unit)? = null
    val onMapLongTapAction: ((Map, Point) -> Unit)? = null
    private var lifecycleEventObserver: LifecycleEventObserver? = null

    init {
        this.map.addInputListener(this)
    }
    override fun onMapTap(p0: Map, p1: Point) {
        onMapTapAction?.invoke(p0, p1)
    }

    override fun onMapLongTap(p0: Map, p1: Point) {
        onMapLongTapAction?.invoke(p0, p1)
    }

    fun attachToLifecycle(lifecycle: Lifecycle) {
        // Наблюдатель за ЖЦ экрана
        lifecycleEventObserver = LifecycleEventObserver { _: LifecycleOwner, event: Lifecycle.Event ->
            when (event) {
                Lifecycle.Event.ON_START -> {
                    this.onStart()
                }
                Lifecycle.Event.ON_STOP -> {
                    this.onStop()
                }
                else -> {}
            }
        }
        lifecycleEventObserver?.let { lifecycle.addObserver(it) }
    }

    fun detachFromLifecycle(lifecycle: Lifecycle) {
        lifecycleEventObserver?.let { lifecycle.removeObserver(it) }
    }

    fun addPoint(point: Point) {
        pointsSportCourts[point] = map.mapObjects.addPlacemark(point).apply {
            setIcon(ImageProvider.fromBitmap(ContextCompat.getDrawable(context, R.drawable.ic_location_black_24)!!.toBitmap()))
            addTapListener { _, _ -> true }
        }
    }
    fun removePoint(point: Point) {
        for ((placePoint, placeObject) in pointsSportCourts) {
            if (abs(point.latitude - placePoint.latitude) < 1e-3 && abs(point.longitude - placePoint.longitude) < 1e-3) {
                map.mapObjects.remove(placeObject)
                pointsSportCourts.remove(placePoint)
                return
            }
        }
    }

    fun drawRunningTrack(points: List<Point>) {
        pointsRunningTrack = points to this.map.mapObjects.addPolyline(Polyline(points)).apply {
            outlineColor = Color.Blue.toArgb()
            setStrokeColor(Color.Green.toArgb())
            strokeWidth = 4f
            outlineWidth = 1.5f
            zIndex = 25f
        }
    }
}
