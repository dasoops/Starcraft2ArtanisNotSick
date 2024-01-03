package com.dasoops.common.screen.setting.option

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import com.dasoops.common.resources.R
import com.dasoops.common.resources.localization.str
import com.dasoops.common.screen.setting.logger

@Composable
fun MapInfo() {
    Title(R.str.screen.setting.mission.title)
    ShowHide()
    AutoScroll()
    MergeSameLevel()
    HalfLevelSuffix()
    TimerSpeed()
}

@Composable
private fun ShowHide() {
    var showHide by setting.showHide

    SwitchOption(
        title = R.str.screen.setting.mission.showHideEvent.title,
        subTitle = R.str.screen.setting.mission.showHideEvent.subTitle,
        checked = showHide,
        onCheckedChange = {
            logger.debug { "setting.showHide change -> $it" }
            showHide = it
        },
    )
}

@Composable
private fun AutoScroll() {
    var autoScroll by setting.autoScroll

    SwitchOption(
        title = R.str.screen.setting.mission.autoScrollEventList.title,
        subTitle = R.str.screen.setting.mission.autoScrollEventList.subTitle,
        checked = autoScroll,
        onCheckedChange = {
            logger.debug { "setting.autoScroll change -> $it" }
            autoScroll = it
        },
    )
}

@Composable
private fun MergeSameLevel() {
    var mergeSameLevel by setting.mergeSameLevel
    Row(
        modifier = Modifier.height(45.dp)
    ) {
        Description(
            modifier = Modifier,
            title = R.str.screen.setting.mission.mergeSameLevel.title,
            subTitle = R.str.screen.setting.mission.mergeSameLevel.subTitle(
                example = if (mergeSameLevel) "T1" else "T1 / T1"
            )
        )
        Switch(
            modifier = Modifier.scale(0.7f).offset(x = (-12).dp),
            checked = mergeSameLevel,
            onCheckedChange = {
                logger.debug { "setting.mergeSameLevel change -> $it" }
                mergeSameLevel = it
            },
        )
    }
}

@Composable
private fun HalfLevelSuffix() {
    var halfLevelSuffix by setting.halfLevelSuffix
    InputOption(
        title = R.str.screen.setting.mission.halfLevelSuffix.title,
        subTitle = R.str.screen.setting.mission.halfLevelSuffix.subTitle(
            example = "T1$halfLevelSuffix"
        ),
        value = halfLevelSuffix,
        onValueChange = {
            halfLevelSuffix = it
            logger.debug { "setting.mergeSameLevel change -> $it" }
        },
    )
}

@Composable
private fun TimerSpeed() {
    var timerSpeed by setting.timerSpeed
    InputOption(
        title = R.str.screen.setting.mission.timerSpeed.title,
        subTitle = R.str.screen.setting.mission.timerSpeed.subTitle(
            timerSpeed = timerSpeed.toString()
        ),
        value = timerSpeed.toString(),
        onValueChange = {
            timerSpeed = it.toFloatOrNull() ?: return@InputOption
            logger.debug { "setting.timerSpeed change -> $it" }
        },
    )
}