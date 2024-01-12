package com.dasoops.starcraft2ArtanisNotSick.common.content.setting

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.dasoops.starcraft2ArtanisNotSick.common.resources.R
import com.dasoops.starcraft2ArtanisNotSick.common.resources.localization.str

@Composable
internal fun MissionInfo(component: SettingComponent) {
    val model by component.state.subscribeAsState()
    Title(R.str.screen.setting.mission.title)
    ShowHide(model.showHide, component::onSelectShowHide)
    AutoScroll(model.autoScroll, component::onSelectAutoScroll)
    MergeSameLevel(model.mergeSameLevel, component::onSelectMergeSameLevel)
    HalfLevelSuffix(model.halfLevelSuffix, component::onChangeHalfLevelSuffix)
    TimerSpeed(model.timerSpeed.toString(), component::onChangeTimerSpeed)
}

@Composable
private fun ShowHide(value: Boolean, onValueChange: (Boolean) -> Unit) {
    SwitchOption(
        title = R.str.screen.setting.mission.showHideEvent.title,
        subTitle = R.str.screen.setting.mission.showHideEvent.subTitle,
        checked = value,
        onCheckedChange = onValueChange,
    )
}

@Composable
private fun AutoScroll(value: Boolean, onValueChange: (Boolean) -> Unit) {
    SwitchOption(
        title = R.str.screen.setting.mission.autoScrollEventList.title,
        subTitle = R.str.screen.setting.mission.autoScrollEventList.subTitle,
        checked = value,
        onCheckedChange = onValueChange,
    )
}

@Composable
private fun MergeSameLevel(value: Boolean, onValueChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.height(45.dp)
    ) {
        Description(
            modifier = Modifier,
            title = R.str.screen.setting.mission.mergeSameLevel.title,
            subTitle = R.str.screen.setting.mission.mergeSameLevel.subTitle(
                example = if (value) "T1" else "T1 / T1"
            )
        )
        Switch(
            modifier = Modifier.scale(0.7f).offset(x = (-12).dp),
            checked = value,
            onCheckedChange = onValueChange,
        )
    }
}

@Composable
private fun HalfLevelSuffix(value: String, onValueChange: (String) -> Unit) {
    InputOption(
        title = R.str.screen.setting.mission.halfLevelSuffix.title,
        subTitle = R.str.screen.setting.mission.halfLevelSuffix.subTitle(
            example = "T1$value"
        ),
        value = value,
        onValueChange = onValueChange,
    )
}

@Composable
private fun TimerSpeed(value: String, onValueChange: (String) -> Unit) {
    InputOption(
        title = R.str.screen.setting.mission.timerSpeed.title,
        subTitle = R.str.screen.setting.mission.timerSpeed.subTitle(
            timerSpeed = value
        ),
        value = value,
        onValueChange = onValueChange,
    )
}
