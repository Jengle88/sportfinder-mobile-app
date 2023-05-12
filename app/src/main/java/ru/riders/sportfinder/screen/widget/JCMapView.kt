package ru.riders.sportfinder.screen.widget

import android.content.Context
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.InputListener
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.mapview.MapView

class JCMapView(
    context: Context,
    private val onMapTapImpl: (map: Map, point: Point) -> Unit,
    private val onMapLongTapImpl: (map: Map, point: Point) -> Unit
) : MapView(context), InputListener {

    init {
        this.map.addInputListener(this)
    }
    override fun onMapTap(p0: Map, p1: Point) {
        onMapTapImpl(p0, p1)
    }

    override fun onMapLongTap(p0: Map, p1: Point) {
        onMapLongTapImpl(p0, p1)
    }
}
