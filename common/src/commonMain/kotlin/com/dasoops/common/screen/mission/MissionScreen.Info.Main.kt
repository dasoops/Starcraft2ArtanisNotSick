package com.dasoops.common.screen.mission

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dasoops.common.LocalState
import com.dasoops.common.resources.AppState
import com.dasoops.common.resources.MissionState
import com.dasoops.common.resources.mission.Mission
import com.dasoops.common.resources.mission.event.NormalTime
import com.dasoops.common.resources.mission.event.RangeTime
import com.dasoops.common.resources.mission.event.sortValue
import kotlinx.coroutines.launch

@Composable
internal fun Main(
    appState: AppState = LocalState.current,
    missionState: MissionState = appState.missionState,
) {
    val scope = rememberCoroutineScope()

    val mission: Mission? by remember { missionState.current }
    val timer: Int by remember { missionState.timer }
    val autoScroll: Boolean by remember { missionState.autoScroll }
    val showHide: Boolean by remember { missionState.showHide }
    val showAggressiveDeploymentEvent: Boolean by remember { missionState.showAggressiveDeploymentEvent }

    val lazyListState = rememberLazyListState()
    val eventList = remember(mission, showHide, showAggressiveDeploymentEvent) {
        mission ?: return@remember emptyList()
        mission!!.event.filter { if (showHide) true else it.show }.sortedBy { it.sortValue }
            .toList()
    }

    Box(Modifier) {
        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            state = lazyListState,
        ) {
            items(eventList) {
                it.Composable()
            }
        }
        VerticalScrollbar(
            modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight().padding(vertical = 8.dp),
            adapter = rememberScrollbarAdapter(lazyListState),
        )
    }

    LaunchedEffect(autoScroll, timer, showHide, eventList) {
        if (!autoScroll) return@LaunchedEffect

        val index = eventList.indexOfFirst {
            when (val first = it.time?.first) {
                is NormalTime -> first.originSeconds == timer
                is RangeTime -> first.begin.originSeconds == timer
                null -> false
                else -> false
            }
        }
        if (index <= 0) return@LaunchedEffect

        scope.launch { lazyListState.animateScrollToItem(index = index) }
    }
}