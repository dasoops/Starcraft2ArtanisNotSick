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
import com.dasoops.common.screen.setting.logger

@Composable
fun MapInfo() {
    Title("MapInfo")
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
        title = "Show hide event",
        subTitle = "Example for SeaRover",
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
        title = "Auto scroll event list",
        subTitle = "",
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
            title = "merge same level",
            subTitle = if (mergeSameLevel) "T1" else "T1 / T1"
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
        title = "half level suffix",
        subTitle = "T1$halfLevelSuffix",
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
        title = "timer speed",
        subTitle = "1 seconds : $timerSpeed seconds for game",
        value = timerSpeed.toString(),
        onValueChange = {
            timerSpeed = it.toFloatOrNull() ?: return@InputOption
            logger.debug { "setting.timerSpeed change -> $it" }
        },
    )
}