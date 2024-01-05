package com.dasoops.common.screen.mission

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dasoops.common.LocalState
import com.dasoops.common.component.Timer
import com.dasoops.common.resources.MissionState

@Composable
internal fun MissionInfo(
    missionState: MissionState = LocalState.current.missionState,
) {
    val openAiChooser by remember { missionState.openAiChooser }

    Timer()
    Column(Modifier.fillMaxWidth().padding(16.dp)) {
        if (!openAiChooser) {
            Top()
            Main()
        } else {
            AiChooser()
        }
    }
}
