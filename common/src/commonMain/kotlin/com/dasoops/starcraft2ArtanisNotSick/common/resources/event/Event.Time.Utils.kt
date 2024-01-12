package com.dasoops.starcraft2ArtanisNotSick.common.resources.event

import com.dasoops.starcraft2ArtanisNotSick.common.resources.R
import com.dasoops.starcraft2ArtanisNotSick.common.resources.mission.missions

/* EventTime.event */
private val eventTimeCache by lazy {
    R.logger.debug { "initialization eventTimeCache" }
    R.missions.flatMap { mission ->
        mission.event.map { it.time to it }
    }.filter { null != it.first }.toMap().mapKeys { it.key!! }
}

val EventTime.event: Event get() = eventTimeCache[this]!!