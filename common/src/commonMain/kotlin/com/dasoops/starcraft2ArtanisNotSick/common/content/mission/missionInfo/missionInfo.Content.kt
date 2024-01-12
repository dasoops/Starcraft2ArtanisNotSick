package com.dasoops.starcraft2ArtanisNotSick.common.content.mission.missionInfo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
internal fun MissionInfo(
    component: MissionInfoComponent,
) {
    Column(Modifier.fillMaxWidth().padding(16.dp)) {
        Top(component)
        Main(component)
    }
}
