package com.dasoops.common.resources.mission.event

import com.dasoops.common.resources.mission.position.Position
import com.dasoops.common.resources.mission.position.position
import com.dasoops.common.util.TimeUnit
import com.dasoops.common.util.UnitTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventTime(
    val trigger: List<Time>,
    val keep: UnitTime? = null,
)

@Serializable
sealed class Time

@Serializable
@SerialName(value = NormalTime.SERIAL_NAME)
class NormalTime(
    override val value: Int,
    override val unit: TimeUnit,
) : Time(), UnitTime {
    companion object Key {
        const val SERIAL_NAME = "Normal"
    }
}

@Serializable
@SerialName(value = RangeTime.SERIAL_NAME)
class RangeTime(
    val begin: UnitTime.DefaultImpl,
    val end: UnitTime.DefaultImpl,
) : Time() {
    companion object Key {
        const val SERIAL_NAME = "Range"
    }
}

@Serializable
@SerialName(value = EventOffsetTime.SERIAL_NAME)
class EventOffsetTime(
    @SerialName(value = "event") private val _event: String,
    val offset: UnitTime.DefaultImpl?,
) : Time() {
    val event get() = event(this._event)

    companion object Key {
        const val SERIAL_NAME = "Event.Offset"
    }
}

@Serializable
@SerialName(value = TriggerPositionTime.SERIAL_NAME)
class TriggerPositionTime(
    @SerialName(value = "position") private val _position: String,
) : Time() {
    val position: Position get() = position(this._position)

    companion object Key {
        const val SERIAL_NAME = "Trigger.Position"
    }
}