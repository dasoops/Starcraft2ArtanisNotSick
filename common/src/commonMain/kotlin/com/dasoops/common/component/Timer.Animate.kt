package com.dasoops.common.component

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.AnimationVector
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.TwoWayConverter
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

data class ControlAnimateValueModel<T>(
    val state: State<T>,
    val stop: () -> Unit,
    val start: () -> Unit,
    val changeValue: (T) -> Unit,
)

@Composable
fun <T, V : AnimationVector> controlAnimateValue(
    targetValue: T,
    typeConverter: TwoWayConverter<T, V>,
    animationSpec: AnimationSpec<T> = remember { spring() },
    visibilityThreshold: T? = null,
    label: String = "ValueAnimation",
    finishedListener: ((T) -> Unit)? = null,
    autoStart: Boolean = true
): ControlAnimateValueModel<T> {
    val coroutineScope = rememberCoroutineScope()
    val animatable = remember { Animatable(targetValue, typeConverter, visibilityThreshold, label) }
    val listener by rememberUpdatedState(finishedListener)
    val animSpec: AnimationSpec<T> by rememberUpdatedState(
        animationSpec.run {
            if (visibilityThreshold != null && this is SpringSpec &&
                this.visibilityThreshold != visibilityThreshold
            ) {
                spring(dampingRatio, stiffness, visibilityThreshold)
            } else {
                this
            }
        }
    )
    val channel = remember { Channel<T>(Channel.CONFLATED) }
    SideEffect {
        channel.trySend(targetValue)
    }
    LaunchedEffect(channel) {
        for (target in channel) {
            // This additional poll is needed because when the channel suspends on receive and
            // two values are produced before consumers' dispatcher resumes, only the first value
            // will be received.
            // It may not be an issue elsewhere, but in animation we want to avoid being one
            // frame late.
            val newTarget = channel.tryReceive().getOrNull() ?: target
            launch {
                if (newTarget != animatable.targetValue) {
                    if (autoStart) animatable.animateTo(newTarget, animSpec)
                    listener?.invoke(animatable.value)
                }
            }
        }
    }
    return ControlAnimateValueModel(
        state = animatable.asState(),
        stop = {
            coroutineScope.launch {
                animatable.stop()
            }
        },
        start = {
            coroutineScope.launch {
                animatable.animateTo(targetValue = targetValue, animationSpec = animationSpec)
            }
        },
        changeValue = {
            coroutineScope.launch {
                animatable.animateTo(targetValue = it, animationSpec = tween(0)).apply {
                    while (endState.isRunning) {
                        // doNothing
                    }
                }
                animatable.animateTo(targetValue = targetValue, animationSpec = animationSpec)
            }
        }
    )
}