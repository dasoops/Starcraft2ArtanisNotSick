package com.dasoops.common.resources.map.position

import com.dasoops.common.resources.R
import com.dasoops.common.resources.map.maps

/* get by id */
private val idEventCache by lazy {
    R.maps.flatMap { map ->
        map.position.map {
            it.id to it
        }
    }.toMap()
}

fun position(id: String) = idEventCache[id]!!

/* Position.event */
private val positionEventCache by lazy {
    R.maps.flatMap { map ->
        map.position.map {
            it to map
        }
    }.toMap()
}
val Position.event get() = positionEventCache[this]!!

/* EventPosition.event */
private val eventPositionEventCache by lazy {
    R.maps.flatMap { map ->
        map.event.map {
            it.position to it
        }
    }.toMap()
}
val EventPosition.event get() = eventPositionEventCache[this]!!