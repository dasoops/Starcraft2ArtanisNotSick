package com.dasoops.starcraft2ArtanisNotSick.common.content.mission

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.dasoops.starcraft2ArtanisNotSick.common.content.RootComponent
import com.dasoops.starcraft2ArtanisNotSick.common.content.mission.aiChooser.AiChooserComponent
import com.dasoops.starcraft2ArtanisNotSick.common.content.mission.missionChooser.MissionChooserComponent
import com.dasoops.starcraft2ArtanisNotSick.common.content.mission.missionInfo.MissionInfoComponent
import com.dasoops.starcraft2ArtanisNotSick.common.resources.Ai
import com.dasoops.starcraft2ArtanisNotSick.common.resources.mission.Mission
import kotlinx.serialization.Serializable
import java.util.UUID

interface MissionComponent {
    val childStack: Value<ChildStack<Config, Child>>

    @Serializable
    sealed interface Config {
        @Serializable
        data class AiChooser(
            val mission: Mission,
            val uuid: String,
            val chooseAi: (Ai) -> Unit,
        ) : Config

        @Serializable
        data object MissionChooser : Config

        @Serializable
        data class MissionInfo(
            val uuid: String,
            val mission: Mission,
        ) : Config {
            override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (javaClass != other?.javaClass) return false

                other as MissionInfo

                return uuid == other.uuid
            }

            override fun hashCode(): Int {
                return uuid.hashCode()
            }
        }
    }

    sealed class Child {
        class AiChooserChild(val component: AiChooserComponent) : Child()
        class MissionChooserChild(val component: MissionChooserComponent) : Child()
        class MissionInfoChild(val component: MissionInfoComponent) : Child()
    }

    class DefaultImpl(
        setting: Value<RootComponent.SettingState>,
        componentContext: ComponentContext,
    ) : MissionComponent, ComponentContext by componentContext {
        private val navigation = StackNavigation<Config>()

        val missionChooserChildInstance: Child.MissionChooserChild by lazy {
            val goMissionInfo: (Mission) -> Unit = {
                navigation.bringToFront(
                    Config.MissionInfo(
                        uuid = UUID.randomUUID().toString(),
                        mission = it,
                    )
                )
            }

            Child.MissionChooserChild(
                component = MissionChooserComponent.DefaultImpl(
                    componentContext = componentContext,
                    goMissionInfo = goMissionInfo
                )
            )
        }

        override val childStack: Value<ChildStack<Config, Child>> = childStack(
            source = navigation,
            serializer = null,
            initialStack = { listOf(Config.MissionChooser) },
            childFactory = { config, _ ->
                when (config) {
                    Config.MissionChooser -> missionChooserChildInstance

                    is Config.AiChooser -> Child.AiChooserChild(
                        component = AiChooserComponent.DefaultImpl(
                            componentContext = componentContext,
                            uuid = config.uuid,
                            mission = config.mission,
                            backMissionInfo = { uuid, mission, ai ->
                                config.chooseAi(ai)
                                navigation.bringToFront(
                                    Config.MissionInfo(
                                        uuid = uuid,
                                        mission = mission,
                                    )
                                )
                            }
                        )
                    )

                    is Config.MissionInfo -> Child.MissionInfoChild(
                        component = MissionInfoComponent.DefaultImpl(
                            componentContext = componentContext,
                            settingState = setting,
                            mission = config.mission,
                            backChoose = { navigation.bringToFront(Config.MissionChooser) },
                            goAiChoose = { mission, chooseAi ->
                                navigation.bringToFront(
                                    Config.AiChooser(
                                        mission = mission,
                                        uuid = config.uuid,
                                        chooseAi = chooseAi,
                                    )
                                )
                            }
                        )
                    )
                }
            },
        )
    }
}
