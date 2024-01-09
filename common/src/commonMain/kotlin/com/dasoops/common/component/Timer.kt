package com.dasoops.common.component

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.dasoops.common.LocalState
import com.dasoops.common.resources.AppState
import com.dasoops.common.resources.MissionState

@Composable
@Suppress("NOTHING_TO_INLINE")
inline fun Timer(
    appState: AppState = LocalState.current,
    missionState: MissionState = appState.missionState,
) {
    val timerSpeed by remember { appState.setting.timerSpeed }
    val timerStart by remember { missionState.timerStart }
    var trigger by remember { mutableStateOf(missionState.timer.state.value) }

    missionState.timer = controlAnimateValue(
        targetValue = trigger,
        typeConverter = Int.VectorConverter,
        animationSpec = tween(
            durationMillis = (Int.MAX_VALUE / 1000 * 1000 / timerSpeed).toInt(),
            easing = LinearEasing
        ),
        autoStart = timerStart,
    )

    DisposableEffect(Unit) {
        trigger = Int.MAX_VALUE / 1000
        onDispose { }
    }

    LaunchedEffect(timerStart) {
        if (timerStart) missionState.timer.start() else missionState.timer.stop()
    }
}