package com.dasoops.starcraft2ArtanisNotSick.common.resources.mission.position

import androidx.compose.runtime.Composable
import com.dasoops.starcraft2ArtanisNotSick.common.resources.R
import com.dasoops.starcraft2ArtanisNotSick.common.resources.localization.str
import com.dasoops.starcraft2ArtanisNotSick.common.resources.mission.Mission
import com.dasoops.starcraft2ArtanisNotSick.common.resources.mission.missions
import com.dasoops.starcraft2ArtanisNotSick.common.resources.mumator.mumators
import com.dasoops.starcraft2ArtanisNotSick.common.util.BaseException

/* get by id */
private val idPositionCache: Map<String, Position> by lazy {
    listOf(
        R.mumators.flatMap { mumator ->
            mumator.position.map {
                it.id to it
            }
        },
        R.missions.flatMap { mission ->
            mission.position.map {
                it.id to it
            }
        }
    ).flatten().toMap()
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
    @Composable get() = R.str.screen.mission.position(this.id).description