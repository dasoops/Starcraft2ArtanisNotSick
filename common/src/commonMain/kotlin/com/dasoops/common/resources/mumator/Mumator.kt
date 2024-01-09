package com.dasoops.common.resources.mumator

import com.dasoops.common.resources.event.Event
import com.dasoops.common.resources.mission.position.Position
import kotlinx.serialization.Serializable

@Serializable
data class Mumator(
    val id: String,
    val event: Collection<Event>,
    val position: Collection<Position>,
) {
    override fun toString(): String {
        return "Mumator(id='$id')"
    }
}