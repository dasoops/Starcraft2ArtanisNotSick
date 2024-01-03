package com.dasoops.common.resources.mission.event

import com.dasoops.common.resources.R
import com.dasoops.common.resources.mission.missions

/* EventTime.event */
private val eventTimeCache by lazy {
    R.missions.flatMap { mission ->
        mission.event.map { it.time to it }
    }.filter { null != it.first }.toMap().mapKeys { it.key!! }
}

val EventTime.event: Event get() = eventTimeCache[this]!!