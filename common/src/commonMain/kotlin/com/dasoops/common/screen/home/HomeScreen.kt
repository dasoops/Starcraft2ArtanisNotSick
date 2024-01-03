package com.dasoops.common.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import com.dasoops.common.LocalState
import com.dasoops.common.component.Timer
import com.dasoops.common.resources.AppState
import com.dasoops.common.resources.MissionState
import com.dasoops.common.resources.R
import com.dasoops.common.resources.localization.str
import com.dasoops.common.resources.mission.Mission
import com.dasoops.common.resources.mission.event.NormalTime
import com.dasoops.common.resources.mission.event.RangeTime
import com.dasoops.common.resources.mission.event.sortValue
import com.dasoops.common.resources.mission.missions
import com.dasoops.common.util.TimeUnit
import com.dasoops.common.util.UnitTime
import com.dasoops.common.util.text
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.launch

val logger = KotlinLogging.logger {}

@Composable
fun HomeScreen() {
    val mission by LocalState.current.missionState.current
    if (mission == null) {
        MissionSelect()
    } else {
        MissionInfo()
    }
}

@Composable
private fun MissionSelect() {
    var mission by LocalState.current.missionState.current
    MissionContainer(
        data = R.missions,
        columnCount = 3,
        modifier = Modifier.padding(top = 24.dp)
    ) {
        if (null == it) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(10.dp).weight(1f),
                content = {}
            )
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable(onClick = {
                    logger.trace { "mission change -> $it" }
                    mission = it
                }).padding(10.dp)
                    .weight(1f),
            ) {
                Image(
                    painter = painterResource(it.image),
                    contentDescription = R.str.screen.mission.mission(it).name,
                    contentScale = ContentScale.FillBounds
                )
                Text(text = R.str.screen.mission.mission(it).name, maxLines = 1)
            }
        }
    }
}

@Composable
private fun MissionInfo(
) {
    Timer()
    Column(Modifier.fillMaxWidth().padding(16.dp)) {
        MissionInfoTop()
        MissionInfoMain()
    }
}

@Composable
private fun MissionInfoMain(
    appState: AppState = LocalState.current,
    missionState: MissionState = appState.missionState,
) {
    val scope = rememberCoroutineScope()

    val mission: Mission? by remember { missionState.current }
    val timer: Int by remember { missionState.timer }
    val autoScroll: Boolean by remember { appState.setting.autoScroll }
    val showHide by remember { appState.setting.showHide }

    val lazyListState = rememberLazyListState()
    val eventList = remember(mission) {
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

    LaunchedEffect(autoScroll, timer, eventList) {
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

private val modifier: RowScope.() -> Modifier = {
    Modifier.weight(1f).fillMaxSize().align(Alignment.CenterVertically)
        .wrapContentHeight(align = Alignment.CenterVertically)
}

@Composable
private fun MissionInfoTop(
    state: MissionState = LocalState.current.missionState
) {
    val mission by remember { state.current }
    if (null == mission) return

    Row(
        modifier = Modifier
            .height(110.dp)
    ) {
        Column(
            modifier = Modifier
                .clickable {
                    logger.trace { "mission change -> null" }
                    state.clear()
                }
            .then(modifier())
            .padding(horizontal = 16.dp, vertical = 6.dp)
        ) {
            Image(
                painter = painterResource(mission!!.image),
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
        TimeText(modifier = modifier())
        Row(
            modifier = Modifier
                .clickable { }
                .then(modifier())
        ) {
            Text(text = "placeHolder")
        }
    }
}

@Composable
private fun TimeText(
    state: MissionState = LocalState.current.missionState,
    modifier: Modifier,
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
            .then(modifier)
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
private inline fun <T> MissionContainer(
    data: Collection<T>,
    modifier: Modifier = Modifier,
    columnCount: Int = 3,
    crossinline content: @Composable RowScope.(T?) -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth()
    ) {
        data.chunked(columnCount).forEach { columnList ->
            item {
                Row {
                    (0 until columnCount).forEach {
                        content(columnList.getOrNull(it))
                    }
                }
            }
        }
    }
}