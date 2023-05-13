package ru.riders.sportfinder.data

import androidx.compose.ui.graphics.painter.Painter
import com.google.gson.annotations.SerializedName
import com.yandex.mapkit.geometry.Point

data class SportCourtsResponse(
    @SerializedName("data")
    val sportCourts: ArrayList<SportCourt>
)

data class SportCourt(
    val name: String,
    val courtId: Long,
    val coordinates: List<Double>,
    val tags: String? = null,
    val distance: Float? = null,
    val temperature: Float? = null,
    val resourceId: Painter? = null
) {
    fun toSportCourtInfo() =
        SportCourtInfo(
            name = name,
            courtId = courtId,
            coordinates = Point(coordinates[0], coordinates[1]),
            tags = tags,
            distance = distance,
            temperature = temperature,
            resourceId = resourceId
        )
}
