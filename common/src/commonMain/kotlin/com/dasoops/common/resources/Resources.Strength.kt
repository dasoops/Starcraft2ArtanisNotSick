package com.dasoops.common.resources

import kotlinx.serialization.Serializable

@Serializable
data class Sterength(
    val level: Int,
    val resources: Int,
)

private fun loadSterength(): List<Sterength> =
    R.resourceConfig<List<Sterength>>("strength.json")


internal val R.sterength: List<Sterength> by lazy { loadSterength() }
private val level2Resource: Map<Int, Int> by lazy {
    R.sterength.associate { it.level to it.resources }
}

fun R.sterength(level: Int): Int = level2Resource[level]!!