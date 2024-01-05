package com.dasoops.common.resources.mission.position

import androidx.compose.runtime.Composable
import com.dasoops.common.resources.R
import com.dasoops.common.resources.localization.str
import com.dasoops.common.resources.mission.Mission
import com.dasoops.common.resources.mission.missions
import com.dasoops.common.util.BaseException

/* get by id */
private val idPositionCache: Map<String, Position> by lazy {
    R.missions.flatMap { mission ->
        mission.position.map {
            it.id to it
        }
    }.toMap()
}

fun position(id: String): Position =
    idPositionCache[id] ?: throw BaseException("undefined position[${id}]")

/* Position.event */
private val positionMissionCache by lazy {
    R.missions.flatMap { mission ->
        mission.position.map {
            it to mission
        }
    }.toMap()
}
val Position.mission: Mission
    get() = positionMissionCache[this]
        ?: throw BaseException("undefined mission[position:${this.id}]")

/* EventPosition.event */
private val eventPositionEventCache by lazy {
    R.missions.flatMap { mission ->
        mission.event.map {
            it.position to it
        }
    }.toMap()
}
val EventPosition.event
    get() = eventPositionEventCache[this]
        ?: throw BaseException("undefined event[eventPosition:$this]")

/* Position.description */
val Position.description
    @Composable get() = R.str.screen.mission.mission(this.mission).position(this.id).description