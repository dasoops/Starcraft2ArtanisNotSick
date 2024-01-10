package com.dasoops.common.screen.mission

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.dasoops.common.component.TimerController
import com.dasoops.common.resources.Ai
import com.dasoops.common.resources.mumator.Mumator
import com.dasoops.common.screen.setting.SettingState

data class LocalMissionStateModel(
    var timer: TimerController<Int> = TimerController.Empty,
    val firstStart: MutableState<Boolean> = mutableStateOf(true),
    val selectMumatorList: SnapshotStateList<Mumator> = mutableStateListOf(),
    val timerStart: MutableState<Boolean> = mutableStateOf(false),
    val autoScroll: MutableState<Boolean> = mutableStateOf(false),
    val showHide: MutableState<Boolean> = mutableStateOf(false),
    val openAiChooser: MutableState<Boolean> = mutableStateOf(false),
    val ai: MutableState<Ai?> = mutableStateOf(null),
) {

    fun clear(settingState: SettingState) {
        firstStart.value = true
        timerStart.value = false
        autoScroll.value = settingState.autoScroll.value
        showHide.value = settingState.showHide.value
        openAiChooser.value = false
        ai.value = null
        selectMumatorList.clear()
    }

    companion object {
        val Default: LocalMissionStateModel = LocalMissionStateModel()
    }
}