package com.dasoops.common.resources.localization

import com.dasoops.common.resources.mission.Mission
import com.dasoops.common.util.BaseException
import kotlinx.serialization.Serializable

@Serializable
data class MissionText(
    val title: String,
    val clickToStart: String,
    val clickToPause: String,
    val chooseAi: String,
    val showAggressiveDeploymentEvent: String,
    val hideEvent: String,
    val autoScroll: String,
    val clickToContinue: String,
    val time: TimeText,
    val level: LevelText,
    val event: EventText,
    private val missions: Map<String, MissionInfoText>,
) {

    fun mission(mission: Mission) = missions[mission.name]!!
}

@Serializable
data class LevelText(
    val title: String,
    val strength: String,
    val tech: String,
    val extra: String,
)

@Serializable
data class TimeText(
    val trigger: String,
    private val timeItem: String,
    private val keep: String,
    private val normal: String,
    private val range: String,
    val eventOffset: EventOffsetText,
    private val triggerPosition: String,
) {
    fun timeItem(index: String, timeText: String) = timeItem.fill(
        "index" to index,
        "timeText" to timeText,
    )

    fun keep(originSeconds: String) = keep.fill(
        "originSeconds" to originSeconds
    )

    fun normal(text: String) = normal.fill(
        "text" to text
    )

    fun range(begin: String, end: String) = range.fill(
        "begin" to begin,
        "end" to end,
    )

    fun triggerPosition(position: String) = triggerPosition.fill(
        "position" to position
    )
}

@Serializable
data class EventOffsetText(
    private val offset: String,
    private val noOffset: String,
) {
    fun offset(event: String, offsetSeconds: String) = offset.fill(
        "event" to event,
        "offsetSeconds" to offsetSeconds
    )

    fun noOffset(event: String) = noOffset.fill(
        "event" to event
    )
}

@Serializable
data class EventText(
    private val assault: String,
    private val award: String,
    private val monopolize: String,
) {
    fun assault(position: String, index: String, description: String) = assault.fill(
        "description" to description,
        "position" to position,
        "index" to index,
    )

    fun award(description: String, index: String) = award.fill(
        "description" to description,
        "index" to index,
    )

    fun monopolize(description: String, index: String) = monopolize.fill(
        "description" to description,
        "index" to index,
    )
}

@Serializable
data class MissionInfoText(
    val name: String,
    private val event: Map<String, EventInfoText>,
    private val position: Map<String, PositionInfoText>,
) {
    fun event(id: String) = event[id] ?: throw BaseException("undefined event[${id}]")
    fun position(id: String) = position[id] ?: throw BaseException("undefined position[${id}]")

}

@Serializable
data class EventInfoText(
    val description: String,
)

@Serializable
data class PositionInfoText(
    val description: String,
)