package ru.riders.sportfinder.domain.model

data class SportCourtListItem(
    val courtId: Long,
    val name: String,
    val tags: String? = null,
    val distance: Float? = null,
    val temperature: Float? = null,
)
