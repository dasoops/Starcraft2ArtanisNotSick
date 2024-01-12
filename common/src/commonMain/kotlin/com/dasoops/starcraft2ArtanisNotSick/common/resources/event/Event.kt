package com.dasoops.starcraft2ArtanisNotSick.common.resources.event

import com.dasoops.starcraft2ArtanisNotSick.common.resources.mission.Target
import com.dasoops.starcraft2ArtanisNotSick.common.resources.mission.position.EventPosition
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed interface Event {
    val id: String
    val show: Boolean
    val time: EventTime?
    val index: Int
    val position: EventPosition
}

@Serializable
sealed class AbstractEvent : Event, Comparable<Event> by EventComparable {
    override fun toString(): String {
        return "${javaClass.simpleName}[id=$id]"
    }
}

@SerialName("Monopolize")
@Serializable
class MonopolizeEvent(
    override val id: String,
    override val show: Boolean,
    override val time: EventTime?,
    override val index: Int,
    override val position: EventPosition,
) : AbstractEvent()

@SerialName("Award")
@Serializable
class AwardEvent(
    override val id: String,
    override val show: Boolean,
    override val time: EventTime?,
    override val index: Int,
    override val position: EventPosition,

    val target: Target? = null
) : AbstractEvent()

@SerialName("Assault")
@Serializable
class AssaultEvent(
    override val id: String,
    override val show: Boolean,
    override val time: EventTime,
    override val index: Int,
    override val position: EventPosition,

    val level: EventLevel,
    val target: Target?
) : AbstractEvent()