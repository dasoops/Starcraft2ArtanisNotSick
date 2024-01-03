package com.dasoops.common.screen.home

import com.dasoops.common.resources.mission.event.AssaultEvent
import com.dasoops.common.resources.mission.event.AwardEvent
import com.dasoops.common.resources.mission.event.Event
import com.dasoops.common.resources.mission.event.EventOffsetTime
import com.dasoops.common.resources.mission.event.EventTime
import com.dasoops.common.resources.mission.event.MonopolizeEvent
import com.dasoops.common.resources.mission.event.NormalTime
import com.dasoops.common.resources.mission.event.RangeTime
import com.dasoops.common.resources.mission.event.Time
import com.dasoops.common.resources.mission.event.TriggerPositionTime
import com.dasoops.common.resources.mission.position.EventPosition
import com.dasoops.common.resources.mission.position.MultiplePosition
import com.dasoops.common.resources.mission.position.Position
import com.dasoops.common.resources.mission.position.SinglePosition
import com.dasoops.common.util.text

/* position */
val Position.text: String
    get() = this.description

val EventPosition.text
    get() = when (this) {
        is SinglePosition -> this.position.text
        is MultiplePosition -> this.position.joinToString { it.text }
    }

/* Time */
val EventTime.first: Time
    get() {
        trigger.firstOrNull { it is NormalTime }?.let { return it }
        trigger.firstOrNull { it is RangeTime }?.let { return it }
        return trigger.first()
    }

val EventTime.text: String
    get() = trigger.joinToString(
        separator = " 或 ",
        postfix = if (null == this.keep) {
            ""
        } else {
            "持续${this.keep.originSeconds}秒"
        },
    ) { it.text }

val EventTime.textFirst: String
    get() = (if (this.trigger.size > 1) "*" else "") + when (this.first) {
        is NormalTime -> (first as NormalTime).text
        is RangeTime -> (first as RangeTime).text
        else -> first.text
    }


val Time.text: String
    get() = when (this) {
        is NormalTime -> this.text

        is RangeTime -> "${begin.text} -> ${end.text}"

        is EventOffsetTime -> event.run {
            if (null == offset) {
                "随事件[${text}]触发"
            } else {
                "[${text}]事件结束${offset.originSeconds}秒后触发"
            }
        }

        is TriggerPositionTime -> {
            "玩家进入${this.position.description}点位后触发"
        }
    }

/* Event */
val Event.text: String
    get() = (if (!show) "*" else "") + when (this) {
        is AssaultEvent -> "${position.text} | 进攻波次 ${index + 1}"
        is AwardEvent -> "奖励波次($description) ${index + 1}"
        is MonopolizeEvent -> "地图事件波次($description) ${index + 1}"
    }