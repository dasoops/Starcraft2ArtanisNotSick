package com.dasoops.starcraft2ArtanisNotSick.common.resources.mission

import kotlinx.serialization.Serializable

@Serializable
data class Target(
    val type: String,
    val target: String? = null,
)