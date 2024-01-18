package com.dasoops.starcraft2ArtanisNotSick.common.resources.event

import androidx.compose.runtime.Composable
import com.dasoops.starcraft2ArtanisNotSick.common.resources.R
import com.dasoops.starcraft2ArtanisNotSick.common.resources.localization.str
import com.dasoops.starcraft2ArtanisNotSick.common.resources.mission.Mission
import com.dasoops.starcraft2ArtanisNotSick.common.resources.mission.missions
import kotlinx.serialization.Serializable

@JvmInline
@Serializable
value class Group(
    val id: String,
)

private val groupMissionCache: Map<Group, Mission> by lazy {
    R.logger.debug { "initlization groupMissionCache" }
    R.missions.flatMap { mission ->
        mission.event
            .filterIsInstance<AssaultEvent>()
            .map { it.group to mission }
    }.toMap()
}

val Group.Mission: Mission get() = groupMissionCache[this]!!

private val groupEventCache: Map<Mission, Map<Group, List<AssaultEvent>>> by lazy {
    R.logger.debug { "initlization groupEventCache" }
    R.missions.associateWith { mission ->
        mission.event
            .filterIsInstance<AssaultEvent>()
            .groupBy { it.group }
    }
}

val Mission.group: Map<Group, List<AssaultEvent>>
    get() = groupEventCache[this]!!

val Group.name: String
    @Composable get() {
        return R.str.screen.mission.group(id).description
    }