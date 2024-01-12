package com.dasoops.starcraft2ArtanisNotSick.common.content.setting

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.update
import com.dasoops.starcraft2ArtanisNotSick.common.component.theme.Theme
import com.dasoops.starcraft2ArtanisNotSick.common.content.RootComponent
import com.dasoops.starcraft2ArtanisNotSick.common.resources.R
import com.dasoops.starcraft2ArtanisNotSick.common.resources.localization.Language
import com.dasoops.starcraft2ArtanisNotSick.common.resources.localization.currentLanguage
import io.github.oshai.kotlinlogging.KotlinLogging

interface SettingComponent {
    val state: MutableValue<RootComponent.SettingState>
    fun onSelectLanguage(target: Language)
    fun onSelectTheme(target: Theme)
    fun onSelectShowHide(target: Boolean)
    fun onSelectAutoScroll(target: Boolean)
    fun onSelectMergeSameLevel(target: Boolean)
    fun onChangeHalfLevelSuffix(target: String)
    fun onChangeTimerSpeed(target: String)

    class DefaultImpl(
        rootComponent: RootComponent,
        componentContext: ComponentContext,
    ) : SettingComponent, ComponentContext by componentContext {
        private val logger = KotlinLogging.logger {}
        override val state: MutableValue<RootComponent.SettingState> = rootComponent.setting

        override fun onSelectLanguage(target: Language) =
            state.update {
                logger.debug { "Setting.Language change: ${it.language} -> $target" }
                R.currentLanguage.update { target }
                it.copy(language = target)
            }

        override fun onSelectTheme(target: Theme) =
            state.update {
                logger.debug { "Setting.theme change: ${it.theme} -> $target" }
                it.copy(theme = target)
            }

        override fun onSelectShowHide(target: Boolean) =
            state.update {
                logger.debug { "Setting.showHide change: ${it.showHide} -> $target" }
                it.copy(showHide = target)
            }

        override fun onSelectAutoScroll(target: Boolean) =
            state.update {
                logger.debug { "Setting.showHide change: ${it.showHide} -> $target" }
                it.copy(autoScroll = target)
            }

        override fun onSelectMergeSameLevel(target: Boolean) =
            state.update {
                logger.debug { "Setting.mergeSameLevel change: ${it.mergeSameLevel} -> $target" }
                it.copy(mergeSameLevel = target)
            }

        override fun onChangeHalfLevelSuffix(target: String) =
            state.update {
                logger.debug { "Setting.halfLevelSuffix change: ${it.halfLevelSuffix} -> $target" }
                it.copy(halfLevelSuffix = target)
            }

        override fun onChangeTimerSpeed(target: String) {
            val floatTarget = target.toFloatOrNull() ?: return
            state.update {
                logger.debug { "Setting.timerSpeed change: ${it.halfLevelSuffix} -> $floatTarget" }
                it.copy(timerSpeed = floatTarget)
            }
        }
    }
}
