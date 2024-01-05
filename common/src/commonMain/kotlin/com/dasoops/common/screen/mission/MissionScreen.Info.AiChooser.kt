package com.dasoops.common.screen.mission

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
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
import androidx.compose.ui.unit.dp
import com.dasoops.common.LocalState
import com.dasoops.common.resources.Ai
import com.dasoops.common.resources.AppState
import com.dasoops.common.resources.MissionState
import com.dasoops.common.resources.R
import com.dasoops.common.resources.Race
import com.dasoops.common.resources.ai
import com.dasoops.common.resources.image
import com.dasoops.common.resources.name


@Composable
fun AiChooser(
    appState: AppState = LocalState.current,
    missionState: MissionState = appState.missionState,
) {
    var openAiChooser by remember { missionState.openAiChooser }
    var ai by remember { missionState.ai }
    var race by remember { mutableStateOf(Race.Default) }

    Column {
        TabRow(
            selectedTabIndex = Race.values().indexOf(race),
        ) {
            Race.values().forEach {
                Tab(
                    selected = race == it,
                    onClick = { race = it },
                ) {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = it.value,
                    )
                }
            }
        }
        LazyColumn {
            items(R.ai(race)) {
                AiBox(
                    ai = it,
                    onSelect = {
                        ai = it
                        openAiChooser = false
                    }
                )
            }
        }
    }
}

@Composable
private fun AiBox(
    ai: Ai,
    onSelect: () -> Unit,
) {
    var expanded by rememberSaveable(ai) { mutableStateOf(false) }
    Surface(
        shape = MaterialTheme.shapes.extraSmall,
        shadowElevation = 2.dp,
        modifier = Modifier.fillMaxWidth().padding(vertical = 3.dp),
    ) {
        Row(
            modifier = Modifier
                .clickable(onClick = {
                    if (!expanded) expanded = true
                    else onSelect()
                })
                .padding(6.dp)
        ) {
            Column {
                Text(
                    text = ai.name,
                )
                Row(modifier = Modifier.padding(6.dp)) {
                    ai.assaults
                        .flatMap { it.units }
                        .distinct()
                        .forEach { UnitImage(it) }
                }
                AnimatedVisibility(
                    visible = expanded
                ) {
                    val lastIndex = ai.assaults.size - 1

                    Divider(modifier = Modifier.height(1.dp))
                    Column {
                        ai.assaults.forEachIndexed { index, it ->
                            Row(
                                modifier = Modifier.padding(6.dp).wrapContentHeight()
                            ) {
                                Text(
                                    text = "T${it.level}",
                                    modifier = Modifier.align(Alignment.CenterVertically),
                                    style = MaterialTheme.typography.bodyLarge,
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                it.units.forEach { UnitImage(it) }
                            }
                            if (index != lastIndex) Divider(modifier = Modifier.height(1.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun UnitImage(it: com.dasoops.common.resources.Unit) {
    Image(
        painter = it.image,
        contentDescription = it.name,
        modifier = Modifier.size(32.dp),
        contentScale = ContentScale.FillBounds,
    )
}