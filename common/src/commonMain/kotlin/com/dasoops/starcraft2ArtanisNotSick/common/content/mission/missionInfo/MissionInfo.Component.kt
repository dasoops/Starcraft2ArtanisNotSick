package com.dasoops.starcraft2ArtanisNotSick.common.content.mission.missionInfo

import com.arkivanov.decompose.Cancellation
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.decompose.value.updateAndGet
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.badoo.reaktive.disposable.scope.DisposableScope
import com.badoo.reaktive.observable.observable
import com.badoo.reaktive.scheduler.Scheduler
import com.badoo.reaktive.scheduler.mainScheduler
import com.dasoops.starcraft2ArtanisNotSick.common.content.RootComponent
import com.dasoops.starcraft2ArtanisNotSick.common.resources.Ai
import com.dasoops.starcraft2ArtanisNotSick.common.resources.event.Event
import com.dasoops.starcraft2ArtanisNotSick.common.resources.event.Group
import com.dasoops.starcraft2ArtanisNotSick.common.resources.event.NormalTime
import com.dasoops.starcraft2ArtanisNotSick.common.resources.event.RangeTime
import com.dasoops.starcraft2ArtanisNotSick.common.resources.event.group
import com.dasoops.starcraft2ArtanisNotSick.common.resources.mission.Mission
import com.dasoops.starcraft2ArtanisNotSick.common.resources.mumator.Mumator
import com.dasoops.starcraft2ArtanisNotSick.common.util.BaseException
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.serialization.Serializable
import java.util.concurrent.atomic.AtomicLong

interface MissionInfoComponent {
    val settingState: Value<RootComponent.SettingState>
    val state: Value<State>
    val mission: Mission
    fun backChoose()
    fun goAiChooser()
    fun onClickTimer()
    fun onChangeShowHide()
    fun onChangeAutoScroll()
    fun onSelectMumator(mumator: Mumator)
    fun onSelectGroup(group: Group)
    fun jumpToEvent(event: Event)

    enum class TimerStatus {
        INITLIZATION_STOP {
            override val next: TimerStatus get() = FIRST_START
        },
        FIRST_START {
            override val next: TimerStatus get() = STOP
        },
        START {
            override val next: TimerStatus get() = STOP
        },
        STOP {
            override val next: TimerStatus get() = START
        }, ;

        abstract val next: TimerStatus
    }

    @Serializable
    data class State(
        val ai: Ai?,
        val timer: Int,
        val timerStatus: TimerStatus,
        val selectMumatorList: List<Mumator>,
        val autoScroll: Boolean,
        val showHide: Boolean,
        val group: Group,
    ) {
        companion object
    }

    class DefaultImpl(
        componentContext: ComponentContext,
        private val backChoose: () -> Unit,
        private val goAiChoose: (mission: Mission, chooseAi: (Ai) -> Unit) -> Unit,
        private val tickScheduler: Scheduler = mainScheduler,
        override val settingState: Value<RootComponent.SettingState>,
        override val mission: Mission,
    ) : MissionInfoComponent, ComponentContext by componentContext {
        private val logger = KotlinLogging.logger {}

        val timer = Timer()
        override val state: MutableValue<State> = MutableValue(
            State(
                ai = null,
                timer = 0,
                timerStatus = TimerStatus.INITLIZATION_STOP,
                selectMumatorList = mutableListOf(),
                autoScroll = settingState.value.autoScroll,
                showHide = false,
                group = mission.group.keys.first(),
            )
        )

        override fun backChoose() {
            this.backChoose.invoke()
        }

        override fun goAiChooser() {
            goAiChoose(mission) { target ->
                state.update {
                    logger.debug { "Mission.ai change: ${it.ai} -> $target" }
                    it.copy(ai = target)
                }
            }
        }

        override fun onClickTimer() {
            val target = state.updateAndGet {
                val target = it.timerStatus.next
                logger.debug { "Mission.timerStatus change: ${it.timerStatus} -> $target" }
                it.copy(timerStatus = target)
            }.timerStatus

            when (target) {
                TimerStatus.INITLIZATION_STOP -> throw BaseException("Unexpect Exception")
                TimerStatus.FIRST_START -> timer.start()
                TimerStatus.START -> timer.start()
                TimerStatus.STOP -> timer.stop()
            }
        }

        override fun onChangeShowHide() {
            state.update {
                val target = !it.showHide
                logger.debug { "Mission.showHide change: ${it.showHide} -> $target" }
                it.copy(showHide = target)
            }
        }

        override fun onChangeAutoScroll() {
            state.update {
                val target = !it.autoScroll
                logger.debug { "Mission.autoScroll change: ${it.autoScroll} -> $target" }
                it.copy(autoScroll = target)
            }
        }

        override fun onSelectMumator(mumator: Mumator) {
            state.update {
                val origin = it.selectMumatorList
                val contain = origin.contains(mumator)
                val target = origin.toMutableList().apply {
                    if (contain) {
                        logger.debug { "Mission.$mumator change: true -> false" }
                        remove(mumator)
                    } else {
                        logger.debug { "Mission.$mumator change: false -> true" }
                        add(mumator)
                    }
                }
                it.copy(selectMumatorList = target)
            }
        }

        override fun onSelectGroup(group: Group) {
            state.update {
                logger.debug { "Mission.autoScroll change: ${it.group} -> $group" }
                it.copy(group = group)
            }
        }

        override fun jumpToEvent(event: Event) {
            val target = when (val time = event.time?.first) {
                is NormalTime -> time.originSeconds
                is RangeTime -> time.begin.originSeconds
                else -> return
            }
            logger.trace { "Mission.timer change -> $target" }

            state.update {
                it.copy(timer = target)
            }
        }

        inner class Timer : InstanceKeeper.Instance, DisposableScope by DisposableScope() {
            var executor: Scheduler.Executor? = null
            var observe: Cancellation? = null

            fun start() {
                observe = settingState.observe { newState ->
                    if (null != executor) {
                        logger.debug { "Timer.executor.cancel()" }
                        executor!!.cancel()
                        executor = null
                    }
                    val periodMillis: Long = (1000L / newState.timerSpeed).toLong()
                    observable { emitter ->
                        executor = tickScheduler.newExecutor()
                        emitter.setDisposable(executor)

                        val count = AtomicLong(-1L)
                        logger.debug { "Timer.startTimer()" }
                        executor!!.submitRepeating(periodMillis, periodMillis) {
                            emitter.onNext(count.addAndGet(1L))
                        }
                    }.subscribeScoped {
                        state.update { it.copy(timer = it.timer + 1) }
                    }
                }
            }

            fun stop() {
                logger.debug { "Timer.executor.cancel()" }
                executor?.cancel()
                observe?.cancel()
            }
        }
    }
}
