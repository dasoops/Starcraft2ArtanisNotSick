package com.dasoops.common.resources.mission

import com.dasoops.common.resources.mission.event.Event
import com.dasoops.common.resources.mission.position.Position
import kotlinx.serialization.Serializable

@Serializable
data class Mission(
    val name: String,
    val image: String,
    val event: Collection<Event>,
    val position: Collection<Position>,
) {
    override fun toString(): String {
        return "Mission(name='$name')"
    }
}