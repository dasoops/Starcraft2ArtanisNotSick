package com.dasoops.starcraft2ArtanisNotSick.common.content.mission

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.dasoops.starcraft2ArtanisNotSick.common.content.mission.aiChooser.AiChooser
import com.dasoops.starcraft2ArtanisNotSick.common.content.mission.missionChooser.MissionSelect
import com.dasoops.starcraft2ArtanisNotSick.common.content.mission.missionInfo.MissionInfo

@Composable
fun MissionScreen(
    component: MissionComponent,
) {
    Children(
        stack = component.childStack,
        modifier = Modifier,
        animation = stackAnimation(fade()),
    ) {
        when (val child = it.instance) {
            is MissionComponent.Child.AiChooserChild -> AiChooser(child.component)
            is MissionComponent.Child.MissionChooserChild -> MissionSelect(child.component)
            is MissionComponent.Child.MissionInfoChild -> MissionInfo(child.component)
        }
    }
}