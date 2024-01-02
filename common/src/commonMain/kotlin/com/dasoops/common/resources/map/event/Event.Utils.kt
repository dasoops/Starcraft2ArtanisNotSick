package com.dasoops.common.resources.map.event

import com.dasoops.common.resources.R
import com.dasoops.common.resources.map.Map
import com.dasoops.common.resources.map.maps

/* get event by id */
private val idEventCache: kotlin.collections.Map<String, Event> by lazy {
    R.maps.flatMap { map ->
        map.event.map { it.id to it }
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

private val eventSortValueCache: kotlin.collections.Map<Event, Int> by lazy {
    R.maps.flatMap { map ->
        map.event.map {
            it to sortValue(it)
        }
    }.toMap()
}

val Event.sortValue: Int get() = eventSortValueCache[this]!!

object MapEventComparable : Comparable<Event> {
    override fun compareTo(other: Event) = other.sortValue
}

/* Event.map */
val eventMapCache: kotlin.collections.Map<Event, Map> by lazy {
    R.maps.flatMap { map ->
        map.event.map { it to map }
    }.toMap(hashMapOf())
}

val Event.map: Map get() = eventMapCache[this]!!
