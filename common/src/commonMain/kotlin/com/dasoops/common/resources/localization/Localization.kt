package com.dasoops.common.resources.localization

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
class Localization(
    val title: String,
    val simpleTitle: String,
    val screen: ScreenText,
) {
    @Transient
    lateinit var language: Language
}

@Serializable
data class ScreenText(
    val setting: SettingText,
    val mission: MissionText,
)