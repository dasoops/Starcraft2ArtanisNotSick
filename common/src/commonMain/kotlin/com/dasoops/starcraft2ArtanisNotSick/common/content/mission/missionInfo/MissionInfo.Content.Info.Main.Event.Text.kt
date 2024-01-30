package com.dasoops.starcraft2ArtanisNotSick.common.content.mission.missionInfo

import androidx.compose.runtime.Composable
import com.dasoops.starcraft2ArtanisNotSick.common.resources.R
import com.dasoops.starcraft2ArtanisNotSick.common.resources.event.AssaultEvent
import com.dasoops.starcraft2ArtanisNotSick.common.resources.event.AwardEvent
import com.dasoops.starcraft2ArtanisNotSick.common.resources.event.Event
import com.dasoops.starcraft2ArtanisNotSick.common.resources.event.EventLevel
import com.dasoops.starcraft2ArtanisNotSick.common.resources.event.EventOffsetTime
import com.dasoops.starcraft2ArtanisNotSick.common.resources.event.EventTime
import com.dasoops.starcraft2ArtanisNotSick.common.resources.event.FixedStrengthLevel
import com.dasoops.starcraft2ArtanisNotSick.common.resources.event.FixedTechLevel
import com.dasoops.starcraft2ArtanisNotSick.common.resources.event.HalfStrengthLevel
import com.dasoops.starcraft2ArtanisNotSick.common.resources.event.Level
import com.dasoops.starcraft2ArtanisNotSick.common.resources.event.MonopolizeEvent
import com.dasoops.starcraft2ArtanisNotSick.common.resources.event.MumatorEvent
import com.dasoops.starcraft2ArtanisNotSick.common.resources.event.NormalLevel
import com.dasoops.starcraft2ArtanisNotSick.common.resources.event.NormalTime
import com.dasoops.starcraft2ArtanisNotSick.common.resources.event.RangeTime
import com.dasoops.starcraft2ArtanisNotSick.common.resources.event.Time
import com.dasoops.starcraft2ArtanisNotSick.common.resources.event.TriggerPositionTime
import com.dasoops.starcraft2ArtanisNotSick.common.resources.event.description
import com.dasoops.starcraft2ArtanisNotSick.common.resources.localization.str
import com.dasoops.starcraft2ArtanisNotSick.common.resources.mission.position.EventPosition
import com.dasoops.starcraft2ArtanisNotSick.common.resources.mission.position.MultipleEventPosition
import com.dasoops.starcraft2ArtanisNotSick.common.resources.mission.position.RandomEventPosition
import com.dasoops.starcraft2ArtanisNotSick.common.resources.mission.position.SingleEventPosition
import com.dasoops.starcraft2ArtanisNotSick.common.resources.mission.position.UnknownEventPosition
import com.dasoops.starcraft2ArtanisNotSick.common.resources.mission.position.description
import com.dasoops.starcraft2ArtanisNotSick.common.util.text

/* position */
internal val EventPosition.text: String
    @Composable get() = when (this) {
        is SingleEventPosition -> this.position.description
        is MultipleEventPosition ->
            "*" + this.position
                .map { it.description }
                .joinToString { it }

        is RandomEventPosition -> {
            val sum = this.position.sumOf { it.weight.toDouble() }

            "*" + this.position
                .map { (it.weight / sum * 100).toInt() to it.position.description }
                .joinToString(separator = ",") { "${it.second}[${it.first}%]" }
        }

        is UnknownEventPosition -> R.str.screen.mission.unknownPosition.description
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

            is MumatorEvent -> R.str.screen.mission.event.assault(
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

/* Level */
@Composable
fun Level.text(halfSuffix: String): String = when (this) {
    is NormalLevel -> "T$level"
    is HalfStrengthLevel -> "T$level$halfSuffix"
    is FixedStrengthLevel -> R.str.screen.mission.level.fixed
    is FixedTechLevel -> R.str.screen.mission.level.fixed
}

@Composable
fun EventLevel.text(halfSuffix: String, mergeSameLevel: Boolean): String = run {
    if (mergeSameLevel && this.tech == this.strength) {
        this.tech.text(halfSuffix)
    } else {
        this.strength.text(halfSuffix) + " / " + this.tech.text(halfSuffix)
    }
}