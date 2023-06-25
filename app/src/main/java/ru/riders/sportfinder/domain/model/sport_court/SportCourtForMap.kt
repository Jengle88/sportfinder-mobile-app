package ru.riders.sportfinder.domain.model.sport_court

import com.google.android.gms.maps.model.LatLng

data class SportCourtForMap(
    val courtId: Long,
    val coordinates: LatLng,
)
