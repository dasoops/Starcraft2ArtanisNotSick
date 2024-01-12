package com.dasoops.starcraft2ArtanisNotSick.common.resources.mission

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import com.dasoops.starcraft2ArtanisNotSick.common.resources.R
import com.dasoops.starcraft2ArtanisNotSick.common.resources.event.Event
import com.dasoops.starcraft2ArtanisNotSick.common.resources.image
import com.dasoops.starcraft2ArtanisNotSick.common.resources.mission
import com.dasoops.starcraft2ArtanisNotSick.common.resources.mission.position.Position
import kotlinx.serialization.Serializable

@Serializable
data class Mission(
    val name: String,
    val event: Collection<Event>,
    val position: Collection<Position>,
) {
    override fun toString(): String {
        return "Mission(name='$name')"
    }
}

val Mission.image: Painter @Composable get() = R.image.mission(this)

private val nameMissionCache by lazy {
    R.logger.debug { "initialization nameMissionCache" }
    R.missions.associateBy { it.name }
}

fun R.mission(name: String): Mission = missionOrNull(name)!!
fun R.missionOrNull(name: String): Mission? = nameMissionCache[name]