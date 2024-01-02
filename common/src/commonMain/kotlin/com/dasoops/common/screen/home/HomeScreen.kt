package com.dasoops.common.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.dasoops.common.resources.MapState
import com.dasoops.common.resources.R
import com.dasoops.common.resources.map.Map
import com.dasoops.common.resources.map.event.NormalTime
import com.dasoops.common.resources.map.event.RangeTime
import com.dasoops.common.resources.map.event.sortValue
import com.dasoops.common.resources.map.maps
import com.dasoops.common.resources.str
import com.dasoops.common.util.TimeUnit
import com.dasoops.common.util.UnitTime
import com.dasoops.common.util.text
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlin.math.max

val logger = KotlinLogging.logger {}

@Composable
fun HomeScreen() {
    val map by LocalState.current.mapState.current
    if (map == null) {
        MapSelect()
    } else {
        MapInfo()
    }
}

@Composable
private fun MapSelect() {
    var map by LocalState.current.mapState.current
    MapContainer(
        data = R.maps,
        columnCount = 3,
        modifier = Modifier.padding(top = 24.dp)
    ) {
        if (null === it) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(10.dp).weight(1f),
                content = {}
            )
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.clickable(onClick = {
                    logger.trace { "map change -> $it" }
                    map = it
                }).padding(10.dp)
                    .weight(1f),
            ) {
                Image(
                    painter = painterResource(it.image),
                    contentDescription = it.name,
                    contentScale = ContentScale.FillBounds
                )
                Text(text = it.name, color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}

@Composable
private fun MapInfo(
) {
    Timer()
    Column(Modifier.fillMaxWidth().padding(16.dp)) {
        MapInfoTop()
        MapInfoMain()
    }
}

@Composable
private fun MapInfoMain(
    appState: AppState = LocalState.current,
    mapState: MapState = appState.mapState,
) {
    val map: Map? by remember { mapState.current }
    val timer: Int by remember { mapState.timer }
    val autoScroll: Boolean by remember { appState.setting.autoScroll }

    val lazyListState = rememberLazyListState()
    val eventList = remember(map) {
        map ?: return@remember emptyList()
        map!!.event.sortedBy { it.sortValue }.toList()
    }

    Box(Modifier) {
        LazyColumn(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            state = lazyListState,
            userScrollEnabled = false,
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
        if (index != -1) lazyListState.animateScrollToItem(max(index - 3, 0))
    }
}

private val modifier: RowScope.() -> Modifier = {
    Modifier.weight(1f).fillMaxSize().align(Alignment.CenterVertically)
        .wrapContentHeight(align = Alignment.CenterVertically)
}

@Composable
private fun MapInfoTop(
    state: MapState = LocalState.current.mapState
) {
    val map by remember { state.current }
    if (null == map) return

    Row(
        modifier = Modifier
            .height(90.dp)
    ) {

    Row(modifier = Modifier
            .clickable {
                logger.trace { "map change -> null" }
                state.clear()
            }
            .then(modifier())
            .padding(16.dp)
        ) {
            Image(
                painter = painterResource(map!!.image),
                contentDescription = map!!.name,
                contentScale = ContentScale.FillBounds
            )
            Spacer(Modifier.width(8.dp))
            Text(text = map!!.name)
        }
        TimeText(modifier = modifier())
        Row(
            modifier = Modifier
                .clickable { }
                .then(modifier())
        ) {
            Text(text = R.str.setting)
        }
    }
}

@Composable
private fun TimeText(
    state: MapState = LocalState.current.mapState,
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
            val nextStatus: String = if (timerStart) {
                "stop"
            } else {
                if (firstStart) "start" else "continue"
            }
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "click to $nextStatus",
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private inline fun <T> MapContainer(
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