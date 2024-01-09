package com.dasoops.common.screen.mission

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.dasoops.common.LocalState
import com.dasoops.common.component.TextDivider
import com.dasoops.common.component.UnitImage
import com.dasoops.common.resources.Ai
import com.dasoops.common.resources.MissionState
import com.dasoops.common.resources.R
import com.dasoops.common.resources.event.AssaultEvent
import com.dasoops.common.resources.event.Event
import com.dasoops.common.resources.event.EventOffsetTime
import com.dasoops.common.resources.event.FixedStrengthLevel
import com.dasoops.common.resources.event.FixedTechLevel
import com.dasoops.common.resources.event.HalfStrengthLevel
import com.dasoops.common.resources.event.NormalLevel
import com.dasoops.common.resources.event.NormalTime
import com.dasoops.common.resources.event.RangeTime
import com.dasoops.common.resources.event.TechLevel
import com.dasoops.common.resources.event.TriggerPositionTime
import com.dasoops.common.resources.icon
import com.dasoops.common.resources.localization.str
import com.dasoops.common.resources.sterength
import kotlinx.coroutines.launch

@Composable
internal fun Event.Composable() = EventBox(
    event = this,
    content = { expanded ->
        Icon(
            painter = painterResource(if (expanded) R.icon.keyboardArrowDown else R.icon.keyboardArrowRight),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.width(40.dp),
        )
        Row(
            modifier = Modifier.weight(1.0f)
        ) {
            if (!expanded) {
                Text(
                    text = time?.textFirst ?: " - ",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Left,
                    modifier = Modifier.weight(1.5f),
                )
                Text(
                    text = this@Composable.position.text,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Left,
                    modifier = Modifier.weight(3f),
                )
                Text(
                    text = this@Composable.text,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Left,
                    modifier = Modifier.weight(3f),
                )
                Text(
                    text = if (this@Composable is AssaultEvent) level.text else "",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Right,
                    modifier = Modifier.weight(1f),
                )
            } else {
                Text(
                    text = this@Composable.text,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    },
    expandedContent = {
        val ai by LocalState.current.missionState.ai
        Column {
            TextDivider(text = R.str.screen.mission.position)
            Text(
                text = " - ${this@Composable.position.text}",
                maxLines = 1,
            )

            if (null != time) {
                TextDivider(text = R.str.screen.mission.time.trigger)
                time!!.keep?.let {
                    Text(text = R.str.screen.mission.time.keep(it.originSeconds.toString()))
                }
                Column {
                    time!!.trigger.forEachIndexed { index, it ->
                        Text(
                            text = R.str.screen.mission.time.timeItem(
                                index = index.toString(),
                                timeText = it.text
                            ),
                            maxLines = 1,
                        )
                    }
                }
            }
            if (this@Composable is AssaultEvent) {
                TextDivider(text = R.str.screen.mission.level.title)

                val strengthStr = R.str.screen.mission.level.strength
                val strengthLevelValue = when (
                    val strengthLevel = this@Composable.level.strength
                ) {
                    is HalfStrengthLevel ->
                        "$strengthStr: T${strengthLevel.level} - ${R.sterength(strengthLevel.level)}"

                    is NormalLevel ->
                        "$strengthStr: T${strengthLevel.level} - ${R.sterength(strengthLevel.level)}"

                    is FixedStrengthLevel ->
                        "$strengthStr: ${strengthLevel.value} "
                }
                Row {
                    Text(
                        text = strengthLevelValue,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }

                val techLevel = this@Composable.level.tech
                AiView(techLevel = techLevel, ai = ai)
            }
        }
    }
)

@Composable
private fun AiView(
    techLevel: TechLevel,
    ai: Ai?,
) {
    Row {
        Text(
            text = "${R.str.screen.mission.level.tech}: ",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.align(Alignment.CenterVertically)
        )

        when (techLevel) {
            is NormalLevel -> {
                Text(
                    text = "T${techLevel.level} - ",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                if (ai == null) {
                    Text(
                        R.str.screen.mission.level.techNotSelect,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                } else {
                    ai.units(techLevel.level).forEach { UnitImage(it) }
                }
            }

            is FixedTechLevel -> {
                techLevel.units.forEach { UnitImage(it) }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun EventBox(
    event: Event,
    content: @Composable RowScope.(/* expanded */Boolean) -> Unit,
    expandedContent: @Composable () -> Unit,
    state: MissionState = LocalState.current.missionState,
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    val timer by remember { state.timer.state }
    val changeTimer = remember { state.timer.changeValue }
    val coroutineScope = rememberCoroutineScope()

    val load = MaterialTheme.colorScheme.primaryContainer
    val unLoad = MaterialTheme.colorScheme.surface
    val background = { it: Float ->
        if (it > 1) {
            Modifier.background(load)
        } else {
            Modifier.background(
                Brush.horizontalGradient(
                    it to load,
                    it to unLoad,
                )
            )
        }
    }
    val colorModifier: Modifier = remember(timer) {
        when (val time = event.time?.first) {
            is NormalTime -> background(1f * timer / time.originSeconds)
            is RangeTime -> background(1f * timer / time.begin.originSeconds)
            is EventOffsetTime -> Modifier
            is TriggerPositionTime -> Modifier
            null -> Modifier
        }
    }
    Surface(
        shape = MaterialTheme.shapes.extraSmall,
        shadowElevation = 2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 6.dp, vertical = 3.dp)
            .run {
                if (!expanded) {
                    this.height(40.dp)
                } else {
                    this.defaultMinSize(minHeight = 160.dp)
                }
            }
    ) {
        Row(
            modifier = Modifier
                .combinedClickable(
                    onLongClick = {
                        val targetValue = when (val time = event.time?.first) {
                            is NormalTime -> time.originSeconds
                            is RangeTime -> time.begin.originSeconds
                            else -> return@combinedClickable
                        }
                        coroutineScope.launch {
                            missionLogger.trace { "changeTimer -> $targetValue" }
                            changeTimer(targetValue)
                        }
                    },
                    onClick = { expanded = !expanded }
                )
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .height(40.dp)
                        .then(colorModifier)
                        .padding(top = 4.dp, bottom = 4.dp, start = 0.dp, end = 16.dp)
                        .wrapContentHeight(Alignment.CenterVertically)
                ) {
                    content(expanded)
                }
                AnimatedVisibility(
                    visible = expanded,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically(),
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 12.dp)
                ) {
                    CompositionLocalProvider(
                        LocalTextStyle provides MaterialTheme.typography.bodyMedium
                    ) {
                        expandedContent()
                    }
                }
            }
        }
    }
}