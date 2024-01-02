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
import com.dasoops.common.resources.MapState
import io.github.oshai.kotlinlogging.KotlinLogging

val logger = KotlinLogging.logger {}

@Composable
@Suppress("NOTHING_TO_INLINE")
inline fun Timer(
    mapState: MapState = LocalState.current.mapState,
) {
    var timer by remember { mapState.timer }
    val timerStart by remember { mapState.timerStart }
    var trigger by remember { mutableStateOf(timer) }

    val (state, stop, start) = controlAnimateValue(
        targetValue = trigger,
        typeConverter = Int.VectorConverter,
        animationSpec = tween(durationMillis = Int.MAX_VALUE / 1000 * 1000, easing = LinearEasing),
        autoStart = timerStart,
    )
    timer = state.value

    DisposableEffect(Unit) {
        trigger = Int.MAX_VALUE / 1000
        onDispose { }
    }

    LaunchedEffect(timerStart) {
        if (timerStart) start() else stop()
    }
}