package com.dasoops.common.component

import androidx.compose.runtime.State

class TimerController<T> @PublishedApi internal constructor(
    val state: State<T>,
    val setValue: suspend (T) -> Unit,
    val stop: suspend () -> Unit,
    val start: suspend () -> Unit,
) {
    operator fun component1() = state
    operator fun component2() = setValue
    operator fun component3() = start
    operator fun component4() = stop

    companion object {
        val Empty = TimerController(
            state = object : State<Int> {
                override val value: Int = 0
            },
            start = { com.dasoops.common.resources.logger.warn { "emptyTimer.start()" } },
            stop = { com.dasoops.common.resources.logger.warn { "emptyTimer.stop()" } },
            setValue = { com.dasoops.common.resources.logger.warn { "emptyTimer.changeValue()" } },
        )
    }
}