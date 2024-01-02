package com.dasoops.common.resources.map

import com.dasoops.common.resources.map.event.Event
import com.dasoops.common.resources.map.position.Position
import kotlinx.serialization.Serializable

@Serializable
data class Map(
    val name: String,
    val image: String,
    val event: Collection<Event>,
    val position: Collection<Position>,
) {
    override fun toString(): String {
        return "Map(name='$name')"
    }
}