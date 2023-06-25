package ru.riders.sportfinder.domain.model.running_track

import com.google.android.gms.maps.model.LatLng

data class RunningTrackVOForMap(
    val trackId: Int,
    val distance: Double,
    val points: List<LatLng>?,
    val tempOnStart: Int?,
    val tempOnEnd: Int?,
)
