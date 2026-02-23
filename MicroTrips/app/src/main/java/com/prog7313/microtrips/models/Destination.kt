package com.prog7313.microtrips.models

import kotlinx.serialization.Serializable

@Serializable
data class Destination (
    val id: Long,
    val name: String,
    val province: String,
    val category: String,
    val image: String,
    val shortDescription: String,
    val timeNeeded: String,
    val bestSeason: String,
    val budget: Budget,
    val tips: List<String>,
    val location: Location,
    val tags: List<String>,
)

data class Budget(
    val transport: Long,
    val food: Long,
    val entry: Long,
    val misc: Long,
)

data class Location(
    val area: String,
    val mapsQuery: String,
)