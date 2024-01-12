package com.dasoops.starcraft2ArtanisNotSick.common.content.mission.aiChooser

import com.arkivanov.decompose.ComponentContext
import com.dasoops.starcraft2ArtanisNotSick.common.resources.Ai
import com.dasoops.starcraft2ArtanisNotSick.common.resources.mission.Mission
import io.github.oshai.kotlinlogging.KotlinLogging

interface AiChooserComponent {
    fun onChooseAi(ai: Ai)

    class DefaultImpl(
        componentContext: ComponentContext,
        private val uuid: String,
        private val mission: Mission,
        private val backMissionInfo: (uuid: String, mission: Mission, ai: Ai) -> Unit
    ) : AiChooserComponent, ComponentContext by componentContext {
        private val logger = KotlinLogging.logger {}

        override fun onChooseAi(ai: Ai) {
            logger.info { "choose Ai: $ai" }
            backMissionInfo(uuid, mission, ai)
        }
    }
}
