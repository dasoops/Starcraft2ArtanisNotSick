package com.dasoops.common.screen.mission

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.staticCompositionLocalOf
import com.dasoops.common.LocalState
import io.github.oshai.kotlinlogging.KotlinLogging

internal val missionLogger = KotlinLogging.logger {}
val LocalMissionState =
    staticCompositionLocalOf { LocalMissionStateModel.Default }

@Composable
fun MissionScreen() {
    val mission by LocalState.current.missionState.current
    CompositionLocalProvider(
        LocalMissionState provides LocalState.current.localMissionStateModel
    ) {
        if (null == mission) {
            MissionSelect()
        } else {
            MissionInfo()
        }
    }
}