package com.dasoops.starcraft2ArtanisNotSick.common.content

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.update
import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import com.arkivanov.essenty.instancekeeper.getOrCreate
import com.dasoops.starcraft2ArtanisNotSick.common.component.theme.Theme
import com.dasoops.starcraft2ArtanisNotSick.common.content.mission.MissionComponent
import com.dasoops.starcraft2ArtanisNotSick.common.content.setting.SettingComponent
import com.dasoops.starcraft2ArtanisNotSick.common.resources.R
import com.dasoops.starcraft2ArtanisNotSick.common.resources.localization.Language
import com.dasoops.starcraft2ArtanisNotSick.common.resources.localization.currentLanguage
import com.dasoops.starcraft2ArtanisNotSick.common.resources.localization.currentSystemLanguage
import com.dasoops.starcraft2ArtanisNotSick.common.resources.localization.str
import kotlinx.serialization.Serializable

interface RootComponent : ComponentContext {
    fun changeScreen(targetConfiguration: Config)

    val childStack: Value<ChildStack<Config, Child>>
    val setting: MutableValue<SettingState>

    @Serializable
    sealed interface Config {
        val index: Int

        @Serializable
        data object Mission : Config {
            override val index: Int = 0
        }

        @Serializable
        data object Setting : Config {
            override val index: Int = 1
        }
    }

    @Serializable
    data class SettingState(
        val language: Language,
        val autoScroll: Boolean,
        val showHide: Boolean,
        val theme: Theme,
        val halfLevelSuffix: String,
        val mergeSameLevel: Boolean,
        val timerSpeed: Float,
    ) : InstanceKeeper.Instance {
        companion object
    }

    sealed class Child(
        val icon: ImageVector,
        val title: @Composable () -> String
    ) {
        class MissionChild(
            val component: MissionComponent,
        ) : Child(
            icon = Icons.Default.Home,
            title = { R.str.screen.mission.title },
        )

        class SettingChild(
            val component: SettingComponent,
        ) : Child(
            icon = Icons.Default.Settings,
            title = { R.str.screen.setting.title },
        )
    }

    class DefaultImpl(
        componentContext: ComponentContext,
    ) : RootComponent, ComponentContext by componentContext {

        private companion object {
            const val KEY_STATE =
                "com.dasoops.starcraft2ArtanisNotSick.common.content.RootComponent.DefaultImpl"
            val SettingState.Companion.Default: SettingState
                get() = SettingState(
                    language = R.currentSystemLanguage,
                    autoScroll = true,
                    showHide = false,
                    theme = Theme.Default,
                    halfLevelSuffix = "(½)",
                    mergeSameLevel = true,
                    timerSpeed = 1.42f
                )
        }

        override val setting: MutableValue<SettingState> =
            MutableValue(instanceKeeper.getOrCreate(KEY_STATE) {
                stateKeeper.consume(KEY_STATE, SettingState.serializer()) ?: SettingState.Default
            })

        init {
            R.currentLanguage.update { setting.value.language }

            stateKeeper.register(
                key = KEY_STATE,
                strategy = SettingState.serializer()
            ) { setting.value }
        }

        private val navigation = StackNavigation<Config>()

        override fun changeScreen(targetConfiguration: Config) {
            navigation.bringToFront(targetConfiguration)
        }

        override val childStack: Value<ChildStack<Config, Child>> = childStack(
            source = navigation,
            serializer = Config.serializer(),
            initialStack = { listOf(Config.Mission, Config.Setting) },
            childFactory = ::child,
        )

        private fun child(config: Config, componentContext: ComponentContext): Child =
            when (config) {
                Config.Mission -> Child.MissionChild(
                    component = MissionComponent.DefaultImpl(
                        setting = setting,
                        componentContext = componentContext
                    )
                )

                Config.Setting -> Child.SettingChild(
                    component = SettingComponent.DefaultImpl(
                        rootComponent = this,
                        componentContext = componentContext
                    )
                )
            }

    }
}
