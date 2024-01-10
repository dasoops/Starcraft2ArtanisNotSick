package com.dasoops.common.component

import androidx.compose.animation.core.Animatable
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
import com.dasoops.common.screen.mission.LocalMissionState
import com.dasoops.common.screen.mission.LocalMissionStateModel
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.launch

@PublishedApi
internal val logger = KotlinLogging.logger { }

@Composable
@Suppress("NOTHING_TO_INLINE")
inline fun Timer(
    appState: AppState = LocalState.current,
    missionState: LocalMissionStateModel = LocalMissionState.current,
) {
    val timerSpeed by remember { appState.settingState.timerSpeed }
    val timerStart by remember { missionState.timerStart }
    var trigger by remember { mutableStateOf(missionState.timer.state.value) }

    val animatable = remember { Animatable(trigger, Int.VectorConverter) }
    missionState.timer = TimerController(
        state = animatable.asState(),
        stop = { animatable.stop() },
        start = {
            animatable.animateTo(
                targetValue = trigger,
                animationSpec = tween(
                    durationMillis = (Int.MAX_VALUE / 1000 * 1000 / timerSpeed).toInt(),
                    easing = LinearEasing
                ),
            )
        },
        setValue = {
            animatable.snapTo(it)
            if (timerStart) missionState.timer.start()
        }
    )
    logger.trace { "trigger: $trigger;timer: ${missionState.timer.state.value}" }

    DisposableEffect(Unit) {
        trigger = Int.MAX_VALUE / 1000
        onDispose { }
    }

    LaunchedEffect(timerStart) {
        launch {
            if (timerStart) missionState.timer.start() else missionState.timer.stop()
        }
    }
}