package com.dasoops.common.screen.mission

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.dasoops.common.LocalState
import com.dasoops.common.resources.AppState
import com.dasoops.common.resources.MissionState
import com.dasoops.common.resources.R
import com.dasoops.common.resources.localization.str
import com.dasoops.common.resources.mission.image
import com.dasoops.common.util.TimeUnit
import com.dasoops.common.util.UnitTime
import com.dasoops.common.util.text

@Composable
internal fun Top(
) {
    Row(
        modifier = Modifier.height(110.dp)
    ) {
        Map()
        Timer()
        Setting()
    }
}

private fun Modifier.topCommon(rowScope: RowScope): Modifier = rowScope.run {
    this@topCommon.fillMaxSize()
        .weight(1f)
        .align(Alignment.CenterVertically)
        .wrapContentHeight(align = Alignment.CenterVertically)
}

@Composable
private fun RowScope.Timer(
    state: MissionState = LocalState.current.missionState,
) {
    var firstStart by rememberSaveable { mutableStateOf(true) }
    val timer by remember { state.timer }
    var timerStart by remember { state.timerStart }
    val timeText = remember(timer) {
        UnitTime.DefaultImpl(value = timer, unit = TimeUnit.SECOND).text
    }
    Row(
        modifier = Modifier
            .clickable {
                logger.trace { "timerStart change -> ${!timerStart}" }
                firstStart = false
                timerStart = !timerStart
            }
            .topCommon(this)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = timeText,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 2.5.em
                ),
                textAlign = TextAlign.Center
            )
            val tips: String = if (timerStart) {
                R.str.screen.mission.clickToPause
            } else {
                if (firstStart) R.str.screen.mission.clickToStart
                else R.str.screen.mission.clickToContinue
            }
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = tips,
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Center
            )
        }
    }
}


@Composable
private fun RowScope.Setting(
    state: AppState = LocalState.current,
    missionState: MissionState = state.missionState,
) {
    var autoScroll by remember { missionState.autoScroll }
    var showAggressiveDeploymentEvent by remember { missionState.showAggressiveDeploymentEvent }
    var showHide by remember { missionState.showHide }
    var openAiChooser by remember { missionState.openAiChooser }
    val ai by remember { missionState.ai }

    Row(
        modifier = Modifier.fillMaxSize().weight(1f).padding(12.dp),
    ) {
        Column(
            modifier = Modifier.weight(1f),
        ) {
            SettingBox(
                text = R.str.screen.mission.chooseAi,
                selected = ai != null,
                onSelect = { openAiChooser = true }
            )

            SettingBox(
                text = R.str.screen.mission.showAggressiveDeploymentEvent,
                selected = showAggressiveDeploymentEvent,
                onSelect = { showAggressiveDeploymentEvent = !showAggressiveDeploymentEvent }
            )
        }
        Column(
            modifier = Modifier.weight(1f),
        ) {
            SettingBox(
                text = R.str.screen.mission.hideEvent,
                selected = showHide,
                onSelect = { showHide = !showHide }
            )
            SettingBox(
                text = R.str.screen.mission.autoScroll,
                selected = autoScroll,
                onSelect = { autoScroll = !autoScroll }
            )
        }
    }
}

@Composable
private fun ColumnScope.SettingBox(
    text: String,
    selected: Boolean,
    onSelect: () -> Unit,
    content: @Composable () -> Unit = {},
) {
    val useColor = if (selected) {
        MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f)
    } else {
        MaterialTheme.colorScheme.background
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .clickable(onClick = {
                onSelect()
            })
            .background(color = useColor)
            .border(
                border = BorderStroke(
                    0.5.dp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
            .wrapContentSize(align = Alignment.Center)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun RowScope.Map(
    state: AppState = LocalState.current,
) {
    val missionState = remember { state.missionState }
    val mission by remember { missionState.current }
    if (null == mission) return

    Column(
        modifier = Modifier
            .clickable {
                logger.trace { "mission change -> null" }
                missionState.clear(state.setting)
            }
            .topCommon(this)
            .padding(horizontal = 16.dp, vertical = 6.dp)
    ) {
        Image(
            painter = mission!!.image,
            contentDescription = R.str.screen.mission.mission(mission!!).name,
            contentScale = ContentScale.FillBounds
        )
        Text(
            text = R.str.screen.mission.mission(mission!!).name,
            textAlign = TextAlign.Center,
            maxLines = 1,
            modifier = Modifier.fillMaxWidth()
        )
    }
}