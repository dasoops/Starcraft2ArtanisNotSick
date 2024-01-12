package com.dasoops.starcraft2ArtanisNotSick.common.resources.event

import androidx.compose.runtime.Composable
import com.dasoops.starcraft2ArtanisNotSick.common.resources.R
import com.dasoops.starcraft2ArtanisNotSick.common.resources.localization.str
import com.dasoops.starcraft2ArtanisNotSick.common.resources.mission.Mission
import com.dasoops.starcraft2ArtanisNotSick.common.resources.mission.missions

/* get event by id */
private val idEventCache: Map<String, Event> by lazy {
    R.logger.debug { "initialization idEventCache" }
    R.missions.flatMap { mission ->
        mission.event.map { it.id to it }
    }.toMap()
}

fun event(id: String): Event = idEventCache[id]!!

/* Event.sortValue */
private fun sortValue(event: Event): Int {
    val time = event.time?.trigger ?: return 0

    time.firstOrNull { it is NormalTime }?.let {
        return (it as NormalTime).originSeconds * 100
    }

    time.firstOrNull { it is RangeTime }?.let {
        return (it as RangeTime).begin.originSeconds * 100
    }

    time.firstOrNull { it is EventOffsetTime }?.let {
        val offsetTarget = (it as EventOffsetTime).event
        return sortValue(offsetTarget) + 1
    }

    return 0
}

private val eventSortValueCache = object : HashMap<Event, Int>() {
    override fun get(key: Event): Int {
        return super.get(key) ?: return sortValue(key)
    }
}

val Event.sortValue: Int get() = eventSortValueCache[this]

object EventComparable : Comparable<Event> {
    override fun compareTo(other: Event) = other.sortValue
}

/* Event.map */
val eventMissionCache: Map<Event, Mission> by lazy {
    R.logger.debug { "initialization eventMissionCache" }
    R.missions.flatMap { mission ->
        mission.event.map { it to mission }
    }.toMap(hashMapOf())
}

val Event.mission: Mission get() = eventMissionCache[this]!!

/* Event.description */
val Event.description
    @Composable get() = R.str.screen.mission.event(this.id).description
