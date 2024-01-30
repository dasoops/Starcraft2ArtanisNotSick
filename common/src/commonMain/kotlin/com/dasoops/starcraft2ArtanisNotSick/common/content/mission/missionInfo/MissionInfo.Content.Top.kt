package com.dasoops.starcraft2ArtanisNotSick.common.content.mission.missionInfo

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.dasoops.starcraft2ArtanisNotSick.common.resources.R
import com.dasoops.starcraft2ArtanisNotSick.common.resources.event.group
import com.dasoops.starcraft2ArtanisNotSick.common.resources.event.name
import com.dasoops.starcraft2ArtanisNotSick.common.resources.localization.str
import com.dasoops.starcraft2ArtanisNotSick.common.resources.mission.Mission
import com.dasoops.starcraft2ArtanisNotSick.common.resources.mission.image
import com.dasoops.starcraft2ArtanisNotSick.common.resources.mumator.mumator
import com.dasoops.starcraft2ArtanisNotSick.common.resources.name
import com.dasoops.starcraft2ArtanisNotSick.common.util.Content
import com.dasoops.starcraft2ArtanisNotSick.common.util.TimeUnit
import com.dasoops.starcraft2ArtanisNotSick.common.util.UnitTime
import com.dasoops.starcraft2ArtanisNotSick.common.util.text

@Composable
internal fun Top(
    component: MissionInfoComponent,
    modifier: Modifier,
) {
    val state by component.state.subscribeAsState()
    Row(
        modifier = modifier,
    ) {
        Map(
            mission = component.mission,
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .clickable { component.backChoose() }
                .padding(horizontal = 16.dp, vertical = 6.dp)
                .align(Alignment.CenterVertically)
                .wrapContentHeight(align = Alignment.CenterVertically),
        )
        Timer(
            time = state.timer,
            timerStatus = state.timerStatus,
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .clickable { component.onClickTimer() }
                .align(Alignment.CenterVertically)
                .wrapContentHeight(align = Alignment.CenterVertically),
        )
        Setting(
            component,
            modifier = Modifier.fillMaxSize().weight(1f).padding(12.dp)
        )
    }
}

@Composable
private fun Map(
    mission: Mission,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        Image(
            painter = mission.image,
            contentDescription = R.str.screen.mission.mission(mission).name,
            contentScale = ContentScale.FillBounds
        )
        Text(
            text = R.str.screen.mission.mission(mission).name,
            textAlign = TextAlign.Center,
            maxLines = 1,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun Timer(
    time: Int,
    timerStatus: MissionInfoComponent.TimerStatus,
    modifier: Modifier = Modifier,
) {
    val timeText = remember(time) {
        UnitTime.DefaultImpl(value = time, unit = TimeUnit.SECOND).text
    }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = timeText,
            style = MaterialTheme.typography.titleLarge.copy(
                fontSize = 2.5.em,
            ),
            textAlign = TextAlign.Center,
        )
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = when (timerStatus) {
                MissionInfoComponent.TimerStatus.INITLIZATION_STOP -> R.str.screen.mission.clickToStart
                MissionInfoComponent.TimerStatus.FIRST_START -> R.str.screen.mission.clickToPause
                MissionInfoComponent.TimerStatus.START -> R.str.screen.mission.clickToPause
                MissionInfoComponent.TimerStatus.STOP -> R.str.screen.mission.clickToContinue
            },
            style = MaterialTheme.typography.titleSmall,
            textAlign = TextAlign.Center,
        )
    }
}


@Composable
private fun Setting(
    component: MissionInfoComponent,
    modifier: Modifier,
) {
    val state by component.state.subscribeAsState()
    var mumatorExpanded by remember { mutableStateOf(false) }
    var groupExpanded by remember { mutableStateOf(false) }
    val str = R.str.screen.mission

    Row(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.weight(1f),
        ) {
            SettingBox(
                text = state.group.name,
                selected = true,
                onSelect = { groupExpanded = true },
            ) {
                DropdownMenu(
                    expanded = groupExpanded,
                    offset = DpOffset(6.dp, 12.dp),
                    onDismissRequest = { groupExpanded = false }
                ) {
                    component.mission.group.keys.forEach {
                        DropdownMenuItem(
                            text = {
                                Text(text = it.name)
                            },
                            onClick = {
                                groupExpanded = false
                                component.onSelectGroup(it)
                            },
                            modifier = Modifier
                                .background(selectedColor(it == state.group)),
                        )
                    }
                }
            }
            SettingBox(
                text = state.ai?.name ?: str.chooseAi,
                selected = null != state.ai,
                onSelect = { component.goAiChooser() },
            )
        }
        Column(
            modifier = Modifier.weight(1f),
        ) {
            SettingBox(
                text = str.autoScroll,
                selected = state.autoScroll,
                onSelect = { component.onChangeAutoScroll() }
            )
            SettingBox(
                text = str.selectMumator,
                selected = state.selectMumatorList.isNotEmpty(),
                onSelect = { mumatorExpanded = true }
            ) {
                DropdownMenu(
                    expanded = mumatorExpanded,
                    offset = DpOffset(6.dp, 12.dp),
                    onDismissRequest = { mumatorExpanded = false }
                ) {
                    DropdownMenuItem(
                        text = {
                            Text(text = str.aggressiveDeployment)
                        },
                        onClick = {
                            mumatorExpanded = false
                            component.onSelectMumator(R.mumator.aggressiveDeployment)
                        },
                        modifier = Modifier
                            .background(selectedColor(state.selectMumatorList.contains(R.mumator.aggressiveDeployment))),
                    )
                    DropdownMenuItem(
                        text = {
                            Text(text = str.voidRifts)
                        },
                        onClick = {
                            mumatorExpanded = false
                            component.onSelectMumator(R.mumator.voidRifts)
                        },
                        modifier = Modifier
                            .background(selectedColor(state.selectMumatorList.contains(R.mumator.voidRifts))),
                    )
                }
            }
        }
    }
}

@Composable
private fun ColumnScope.SettingBox(
    text: String,
    selected: Boolean,
    onSelect: (() -> Unit)?,
    content: Content? = null,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .run {
                if (null != onSelect) {
                    this.clickable(onClick = {
                        onSelect()
                    })
                } else {
                    this
                }
            }
            .background(color = selectedColor(selected))
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
        content?.invoke()
    }
}

@Composable
private fun selectedColor(selected: Boolean) = if (selected) {
    MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f)
} else {
    MaterialTheme.colorScheme.background
}