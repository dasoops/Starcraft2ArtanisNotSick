package com.dasoops.starcraft2ArtanisNotSick.common.content.mission.missionInfo

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
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.dasoops.starcraft2ArtanisNotSick.common.resources.event.AssaultEvent
import com.dasoops.starcraft2ArtanisNotSick.common.resources.event.Event
import com.dasoops.starcraft2ArtanisNotSick.common.resources.event.NormalTime
import com.dasoops.starcraft2ArtanisNotSick.common.resources.event.RangeTime
import com.dasoops.starcraft2ArtanisNotSick.common.resources.event.sortValue
import kotlinx.coroutines.launch

@Composable
internal fun Main(
    component: MissionInfoComponent,
) {
    val mission = component.mission
    val state by component.state.subscribeAsState()

    val eventList: List<Event> = remember(state) {
        val originEventList = mission.event
            .filter {
                if (it !is AssaultEvent) true
                else state.group == it.group
            }
            .toMutableList()

        state.selectMumatorList.forEach { originEventList.addAll(it.event) }

        originEventList
            .filter { if (state.showHide) true else it.show }
            .sortedBy { it.sortValue }
    }

    val lazyListState = rememberLazyListState()
    Box(Modifier) {
        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            state = lazyListState,
        ) {
            items(eventList) {
                it.Composable(
                    settingValue = component.settingState,
                    jumpToEvent = component::jumpToEvent,
                    timer = state.timer,
                    ai = state.ai,
                )
            }
        }
        VerticalScrollbar(
            modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight().padding(vertical = 8.dp),
            adapter = rememberScrollbarAdapter(lazyListState),
        )
    }

    val scope = rememberCoroutineScope()

    LaunchedEffect(state, eventList) {
        if (!state.autoScroll) return@LaunchedEffect

        val index = eventList.indexOfFirst {
            when (val first = it.time?.first) {
                is NormalTime -> first.originSeconds == state.timer
                is RangeTime -> first.begin.originSeconds == state.timer
                null -> false
                else -> false
            }
        }
        if (index <= 0) return@LaunchedEffect

        scope.launch { lazyListState.animateScrollToItem(index = index) }
    }
}
