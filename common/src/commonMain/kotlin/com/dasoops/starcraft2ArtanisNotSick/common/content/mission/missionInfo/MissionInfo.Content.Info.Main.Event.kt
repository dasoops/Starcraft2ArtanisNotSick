package com.dasoops.starcraft2ArtanisNotSick.common.content.mission.missionInfo

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
import androidx.compose.runtime.MutableState
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
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.arkivanov.decompose.value.Value
import com.dasoops.starcraft2ArtanisNotSick.common.component.TextDivider
import com.dasoops.starcraft2ArtanisNotSick.common.component.UnitImage
import com.dasoops.starcraft2ArtanisNotSick.common.content.RootComponent
import com.dasoops.starcraft2ArtanisNotSick.common.resources.Ai
import com.dasoops.starcraft2ArtanisNotSick.common.resources.R
import com.dasoops.starcraft2ArtanisNotSick.common.resources.event.AssaultEvent
import com.dasoops.starcraft2ArtanisNotSick.common.resources.event.Event
import com.dasoops.starcraft2ArtanisNotSick.common.resources.event.EventOffsetTime
import com.dasoops.starcraft2ArtanisNotSick.common.resources.event.FixedStrengthLevel
import com.dasoops.starcraft2ArtanisNotSick.common.resources.event.FixedTechLevel
import com.dasoops.starcraft2ArtanisNotSick.common.resources.event.HalfStrengthLevel
import com.dasoops.starcraft2ArtanisNotSick.common.resources.event.MumatorEvent
import com.dasoops.starcraft2ArtanisNotSick.common.resources.event.NormalLevel
import com.dasoops.starcraft2ArtanisNotSick.common.resources.event.NormalTime
import com.dasoops.starcraft2ArtanisNotSick.common.resources.event.RangeTime
import com.dasoops.starcraft2ArtanisNotSick.common.resources.event.TechLevel
import com.dasoops.starcraft2ArtanisNotSick.common.resources.event.TriggerPositionTime
import com.dasoops.starcraft2ArtanisNotSick.common.resources.icon
import com.dasoops.starcraft2ArtanisNotSick.common.resources.localization.str
import com.dasoops.starcraft2ArtanisNotSick.common.resources.sterength
import com.dasoops.starcraft2ArtanisNotSick.common.util.BaseException
import kotlinx.coroutines.launch

@Composable
internal fun Event.Composable(
    settingValue: Value<RootComponent.SettingState>,
    ai: Ai?,
    timer: Int,
    jumpToEvent: (event: Event) -> Unit,
) = EventBox(
    event = this,
    timer = timer,
    jumpToEvent = jumpToEvent,
    content = { expanded ->
        val setting by settingValue.subscribeAsState()
        Icon(
            painter = painterResource(if (expanded) R.icon.keyboardArrowDown else R.icon.keyboardArrowRight),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.width(40.dp),
        )
        Row(
            modifier = Modifier.weight(1.0f)
        ) {

            val levelText =
                if (this@Composable is AssaultEvent)
                    level.text(
                        halfSuffix = setting.halfLevelSuffix,
                        mergeSameLevel = setting.mergeSameLevel,
                    )
                else
                    ""

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
                    text = levelText,
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
            if (this@Composable is AssaultEvent || this@Composable is MumatorEvent) {
                TextDivider(text = R.str.screen.mission.level.title)

                val strengthStr = R.str.screen.mission.level.strength
                val eventLevel = when (this@Composable) {
                    is AssaultEvent -> this@Composable.level
                    is MumatorEvent -> this@Composable.level
                    else -> throw BaseException("unExpected")
                }
                val strengthLevelValue = when (val strengthLevel = eventLevel.strength) {
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
                    /*Text(
                        text = "(",
                    )
                    Image(
                        painter = painterResource(R.icon.minerals),
                        contentDescription = "minerals",
                    )
                    Text(
                        text = " + ",
                    )
                    Image(
                        painter = painterResource(R.icon.vespene),
                        contentDescription = "vespene",
                    )
                    Text(
                        text = ")",
                    )*/
                }

                val techLevel = eventLevel.tech
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

@Composable
private fun EventBox(
    event: Event,
    timer: Int,
    jumpToEvent: (Event) -> Unit,
    content: @Composable RowScope.(expanded: Boolean) -> Unit,
    expandedContent: @Composable () -> Unit,
) {
    val expandedStatus = rememberSaveable { mutableStateOf(false) }
    val expanded by remember { expandedStatus }
    val colorModifier = rememberColorModifier(
        timer = timer,
        event = event
    )
    val clickableMidifier = rememberClickableMidifier(
        event = event,
        jumpToEvent = jumpToEvent,
        expandedState = expandedStatus,
    )

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
            modifier = clickableMidifier
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

@Composable
@OptIn(ExperimentalFoundationApi::class)
fun rememberClickableMidifier(
    jumpToEvent: (event: Event) -> Unit,
    event: Event,
    expandedState: MutableState<Boolean>,
): Modifier = run {
    val coroutineScope = rememberCoroutineScope()
    var expanded by remember { expandedState }

    Modifier.combinedClickable(
        onLongClick = {
            coroutineScope.launch { jumpToEvent(event) }
        },
        onClick = { expanded = !expanded }
    )
}

@Composable
private fun rememberColorModifier(
    timer: Int,
    event: Event,
): Modifier {

    val load = MaterialTheme.colorScheme.primaryContainer
    val unLoad = MaterialTheme.colorScheme.surface
    val background = { capacity: Float ->
        if (capacity > 1) {
            Modifier.background(load)
        } else {
            Modifier.background(
                Brush.horizontalGradient(
                    capacity to load,
                    capacity to unLoad,
                )
            )
        }
    }
    return remember(timer) {
        when (val time = event.time?.first) {
            is NormalTime -> background(1f * timer / time.originSeconds)
            is RangeTime -> background(1f * timer / time.begin.originSeconds)
            is EventOffsetTime -> Modifier
            is TriggerPositionTime -> Modifier
            null -> Modifier
        }
    }
}
