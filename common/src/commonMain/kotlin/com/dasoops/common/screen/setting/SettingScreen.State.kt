package com.dasoops.common.screen.setting

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.dasoops.common.component.theme.Theme
import com.dasoops.common.util.MutableStateSerializer
import kotlinx.serialization.Serializable


@Serializable
class Setting(
    @Serializable(with = MutableStateSerializer::class)
    val autoScroll: MutableState<Boolean>,
    @Serializable(with = MutableStateSerializer::class)
    val showHide: MutableState<Boolean>,
    @Serializable(with = MutableStateSerializer::class)
    val theme: MutableState<Theme>,
    @Serializable(with = MutableStateSerializer::class)
    val halfLevelSuffix: MutableState<String>,
    @Serializable(with = MutableStateSerializer::class)
    val mergeSameLevel: MutableState<Boolean>,
    @Serializable(with = MutableStateSerializer::class)
    val timerSpeed: MutableState<Float>,
) {

    companion object {
        val Default
            get() = Setting(
                autoScroll = mutableStateOf(false),
                showHide = mutableStateOf(false),
                theme = mutableStateOf(Theme.Default),
                halfLevelSuffix = mutableStateOf("(½)"),
                mergeSameLevel = mutableStateOf(true),
                timerSpeed = mutableStateOf(1.42f)
            )
    }
}
