package com.dasoops.common.screen.mission

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.dasoops.common.resources.MissionState
import com.dasoops.common.resources.R
import com.dasoops.common.resources.icon
import com.dasoops.common.resources.mission.event.AssaultEvent
import com.dasoops.common.resources.mission.event.AwardEvent
import com.dasoops.common.resources.mission.event.Event
import com.dasoops.common.resources.mission.event.MonopolizeEvent
import com.dasoops.common.resources.mission.event.NormalTime
import com.dasoops.common.resources.mission.event.RangeTime
import com.dasoops.common.resources.mission.event.text

@Composable
fun Event.Composable() = EventBox(
    event = this,
    content = {
        Icon(
            painter = painterResource(if (it) R.icon.keyboardArrowDown else R.icon.keyboardArrowRight),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.width(40.dp),
        )
        Row(
            modifier = Modifier.weight(1.0f)
        ) {
            Text(
                text = time?.textFirst ?: " - ",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(0.5f),
            )

            Spacer(Modifier.width(20.dp))

            Text(
                text = this@Composable.text,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f),
            )
        }
        Box(modifier = Modifier.width(110.dp)) {
            if (this@Composable is AssaultEvent) {
                Text(
                    text = level.text,
                    modifier = Modifier.width(110.dp),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Right
                )
            }
        }
    },
    expandedContent = {
        Text(text = this.id)
    }
)

@Composable
fun EventBox(
    event: Event,
    content: @Composable RowScope.(/* expanded */Boolean) -> Unit,
    expandedContent: @Composable () -> Unit,
    state: MissionState = LocalState.current.missionState,
    timer: Int = state.timer.value,
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    val load = MaterialTheme.colorScheme.primaryContainer
    val unLoad = MaterialTheme.colorScheme.surface
    val background = { it: Float ->
        if (it > 1) {
            Modifier.background(load)
        } else {
            Modifier.background(
                Brush.horizontalGradient(
                    0f to load,
                    it to load,
                    it to unLoad,
                    1f to unLoad,
                )
            )
        }
    }
    val colorModifier: Modifier = remember(timer) {
        when (val time = event.time?.first) {
            is NormalTime -> background(1f * timer / time.originSeconds)
            is RangeTime -> background(1f * timer / time.begin.originSeconds)
            else -> Modifier
        }
    }
    Surface(
        shape = MaterialTheme.shapes.extraSmall,
        shadowElevation = 2.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 6.dp, vertical = 3.dp)
            .height((if (!expanded) 40 else 160).dp),
    ) {
        Row(
            modifier = Modifier.clickable { expanded = !expanded }
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
                ) {
                    expandedContent()
                }
            }
        }
    }
}

@Composable
fun AssaultEvent.Composable() = EventBox(
    event = this,
    content = {
        Icon(
            painter = painterResource(if (it) R.icon.keyboardArrowDown else R.icon.keyboardArrowRight),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.width(40.dp),
        )
        Row(
            modifier = Modifier.weight(1.0f)
        ) {
            Text(
                text = time.textFirst,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(0.7f),
            )

            Spacer(Modifier.width(20.dp))

            Text(
                text = text,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f),
            )
        }
        Text(
            text = level.text,
            modifier = Modifier.width(110.dp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Right
        )
    },
    expandedContent = {
        Text(text = this.id)
    }
)

@Composable
fun AwardEvent.Composable() = EventBox(
    event = this,
    content = {
        Icon(
            painter = painterResource(if (it) R.icon.keyboardArrowDown else R.icon.keyboardArrowRight),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.width(40.dp),
        )
        Row(
            modifier = Modifier.weight(1.0f)
        ) {
            Text(
                text = time?.textFirst ?: "",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(0.7f),
            )

            Spacer(Modifier.width(20.dp))

            Text(
                text = text,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f),
            )
        }
        Spacer(modifier = Modifier.width(110.dp))
    },
    expandedContent = {
        Text(text = this.id)
    }
)

@Composable
fun MonopolizeEvent.Composable() = EventBox(
    event = this,
    content = {
        Icon(
            painter = painterResource(if (it) R.icon.keyboardArrowDown else R.icon.keyboardArrowRight),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.width(40.dp),
        )
        Row(
            modifier = Modifier.weight(1.0f)
        ) {
            Text(
                text = time?.textFirst ?: "",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(0.7f),
            )

            Spacer(Modifier.width(20.dp))

            Text(
                text = text,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f),
            )
        }
        Spacer(modifier = Modifier.width(110.dp))
    },
    expandedContent = {
        Text(text = this.id)
    }
)