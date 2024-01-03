package com.dasoops.common.resources.localization

import kotlinx.serialization.Serializable

@Serializable
data class MissionText(
    val title: String,
    val clickToStart: String,
    val clickToPause: String,
    val clickToContinue: String,
    private val missions: Map<String, MissionInfoText>,
) {
    fun mission(map: com.dasoops.common.resources.map.Map) = missions[map.name]!!
}

@Serializable
data class MissionInfoText(
    val name: String,
)