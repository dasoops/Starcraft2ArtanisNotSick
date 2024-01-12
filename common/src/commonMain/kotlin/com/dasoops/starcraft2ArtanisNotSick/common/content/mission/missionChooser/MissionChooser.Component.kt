package com.dasoops.starcraft2ArtanisNotSick.common.content.mission.missionChooser

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.dasoops.starcraft2ArtanisNotSick.common.resources.mission.Mission
import io.github.oshai.kotlinlogging.KotlinLogging

interface MissionChooserComponent {
    val currentMission: Mission?
    fun chooseMission(target: Mission)

    class DefaultImpl(
        componentContext: ComponentContext,
        val goMissionInfo: (mission: Mission) -> Unit
    ) : MissionChooserComponent, ComponentContext by componentContext {
        private val logger = KotlinLogging.logger {}
        val model: MutableValue<Model> = MutableValue(Model())

        data class Model(
            val mission: Mission? = null
        )

        override val currentMission: Mission? get() = model.value.mission
        override fun chooseMission(target: Mission) {
            logger.debug { "Mission.mission change: -> $target" }
            goMissionInfo(target)
        }
    }
}
