package com.dasoops.common.screen.mission

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.dasoops.common.LocalState
import io.github.oshai.kotlinlogging.KotlinLogging

val logger = KotlinLogging.logger {}

@Composable
fun MissionScreen() {
    val mission by LocalState.current.missionState.current
    if (mission == null) {
        MissionSelect()
    } else {
        MissionInfo()
    }
}