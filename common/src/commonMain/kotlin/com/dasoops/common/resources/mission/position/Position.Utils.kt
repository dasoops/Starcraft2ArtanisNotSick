package com.dasoops.common.resources.mission.position

import com.dasoops.common.resources.R
import com.dasoops.common.resources.mission.missions

/* get by id */
private val idEventCache by lazy {
    R.missions.flatMap { mission ->
        mission.position.map {
            it.id to it
        }
    }.toMap()
}

fun position(id: String) = idEventCache[id]!!

/* Position.event */
private val positionEventCache by lazy {
    R.missions.flatMap { mission ->
        mission.position.map {
            it to mission
        }
    }.toMap()
}
val Position.event get() = positionEventCache[this]!!

/* EventPosition.event */
private val eventPositionEventCache by lazy {
    R.missions.flatMap { mission ->
        mission.event.map {
            it.position to it
        }
    }.toMap()
}
val EventPosition.event get() = eventPositionEventCache[this]!!