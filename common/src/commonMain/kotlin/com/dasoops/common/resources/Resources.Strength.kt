package com.dasoops.common.resources

import kotlinx.serialization.Serializable

@Serializable
data class Strength(
    val level: Int,
    val resources: Int,
)

private fun loadSterength(): List<Strength> =
    R.resourceConfig<List<Strength>>("strength.json")


internal val R.strength: List<Strength> by lazy { loadSterength() }
private val level2Resource: Map<Int, Int> by lazy {
    R.strength.associate { it.level to it.resources }
}

fun R.sterength(level: Int): Int = level2Resource[level]!!