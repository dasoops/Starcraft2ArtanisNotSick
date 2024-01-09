package com.dasoops.common.screen.mission

import androidx.compose.runtime.Composable
import com.dasoops.common.resources.R
import com.dasoops.common.resources.event.AssaultEvent
import com.dasoops.common.resources.event.AwardEvent
import com.dasoops.common.resources.event.Event
import com.dasoops.common.resources.event.EventOffsetTime
import com.dasoops.common.resources.event.EventTime
import com.dasoops.common.resources.event.MonopolizeEvent
import com.dasoops.common.resources.event.NormalTime
import com.dasoops.common.resources.event.RangeTime
import com.dasoops.common.resources.event.Time
import com.dasoops.common.resources.event.TriggerPositionTime
import com.dasoops.common.resources.event.description
import com.dasoops.common.resources.localization.str
import com.dasoops.common.resources.mission.position.EventPosition
import com.dasoops.common.resources.mission.position.MultiplePosition
import com.dasoops.common.resources.mission.position.RandomPosition
import com.dasoops.common.resources.mission.position.SinglePosition
import com.dasoops.common.resources.mission.position.UnknownPosition
import com.dasoops.common.resources.mission.position.description
import com.dasoops.common.util.text

/* position */
internal val EventPosition.text: String
    @Composable get() = when (this) {
        is SinglePosition -> this.position.description
        is MultiplePosition ->
            "*" + this.position
                .map { it.description }
                .joinToString { it }

        is RandomPosition ->
            "*" + this.position
                .map { it.position.description }
                .joinToString(separator = " or ") { it }

        is UnknownPosition -> R.str.screen.mission.unknownPosition.description
    }

/* Time */
internal val EventTime.first: Time
    get() {
        trigger.firstOrNull { it is NormalTime }?.let { return it }
        trigger.firstOrNull { it is RangeTime }?.let { return it }
        return trigger.first()
    }

internal val EventTime.textFirst: String
    @Composable get() = (if (this.trigger.size > 1) "*" else "") + when (this.first) {
        is NormalTime -> (first as NormalTime).text
        is RangeTime -> (first as RangeTime).text
        else -> first.text
    }


internal val Time.text: String
    @Composable get() = when (this) {
        is NormalTime -> R.str.screen.mission.time.normal(
            text = this.text
        )

        is RangeTime -> R.str.screen.mission.time.range(
            begin = begin.text,
            end = end.text,
        )

        is EventOffsetTime -> run {
            if (null == offset) {
                R.str.screen.mission.time.eventOffset.noOffset(
                    event = this.event.text
                )
            } else {
                R.str.screen.mission.time.eventOffset.offset(
                    event = this.event.text,
                    offsetSeconds = this.offset.originSeconds.toString()
                )
            }
        }

        is TriggerPositionTime -> {
            R.str.screen.mission.time.triggerPosition(
                position = this.position.description
            )
        }
    }

/* Event */
internal val Event.text: String
    @Composable get() = run {
        (if (!show) "*" else "") + when (this) {
            is AssaultEvent -> R.str.screen.mission.event.assault(
                description = this.description,
                position = this.position.text,
                index = (this.index + 1).toString(),
            )

            is AwardEvent -> R.str.screen.mission.event.award(
                description = this.description,
                index = (this.index + 1).toString(),
            )

            is MonopolizeEvent -> R.str.screen.mission.event.monopolize(
                description = this.description,
                index = (this.index + 1).toString(),
            )
        }
    }