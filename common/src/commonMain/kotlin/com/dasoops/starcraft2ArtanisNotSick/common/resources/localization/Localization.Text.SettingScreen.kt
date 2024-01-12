package com.dasoops.starcraft2ArtanisNotSick.common.resources.localization

import kotlinx.serialization.Serializable

@Serializable
data class SettingText(
    val title: String,
    val github: GithubText,
    val appearance: AppearanceText,
    val mission: MissionText,
) {
    @Serializable
    data class GithubText(
        val name: String,
        val sourceCode: String,
    )

    @Serializable
    data class AppearanceText(
        val title: String,
        val language: LanguageText,
        val theme: ThemeText,
    ) {
        @Serializable
        data class LanguageText(
            val title: String,
            val subTitle: String,
        )

        @Serializable
        data class ThemeText(
            val title: String,
            val subTitle: String,
        )
    }

    @Serializable
    data class MissionText(
        val title: String,
        val showHideEvent: ShowHideEventText,
        val autoScrollEventList: AutoScrollEventListText,
        val mergeSameLevel: MergeSameLevelText,
        val halfLevelSuffix: HalfLevelSuffixText,
        val timerSpeed: TimerSpeedText,
    ) {
        @Serializable
        data class ShowHideEventText(
            val title: String,
            val subTitle: String,
        )

        @Serializable
        data class AutoScrollEventListText(
            val title: String,
            val subTitle: String,
        )

        @Serializable
        data class MergeSameLevelText(
            val title: String,
            private val subTitle: String,
        ) {
            fun subTitle(example: String) =
                subTitle.fill(
                    "example" to example
                )
        }

        @Serializable
        data class HalfLevelSuffixText(
            val title: String,
            private val subTitle: String,
        ) {
            fun subTitle(example: String) =
                subTitle.fill(
                    "example" to example
                )
        }

        @Serializable
        data class TimerSpeedText(
            val title: String,
            private val subTitle: String,
        ) {
            fun subTitle(timerSpeed: String) =
                subTitle.fill(
                    "timerSpeed" to timerSpeed
                )
        }
    }
}