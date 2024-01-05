package com.dasoops.common.resources.mission

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import com.dasoops.common.resources.R
import com.dasoops.common.resources.image
import com.dasoops.common.resources.mission
import com.dasoops.common.resources.mission.event.Event
import com.dasoops.common.resources.mission.position.Position
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