package com.dasoops.common.resources.map.event

import com.dasoops.common.resources.R
import com.dasoops.common.resources.map.maps

/* EventTime.event */
private val eventTimeCache by lazy {
    R.maps.flatMap { map ->
        map.event.map { it.time to it }
    }.filter { null != it.first }.toMap().mapKeys { it.key!! }
}

val EventTime.event: Event get() = eventTimeCache[this]!!