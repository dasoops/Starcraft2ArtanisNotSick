package com.dasoops.common.resources.mumator

import com.dasoops.common.resources.R
import com.dasoops.common.resources.resourceConfig

private fun loadMumators(): Collection<Mumator> =
    R.resourceConfig<Collection<Mumator>>("mumator.json")

internal val R.mumators: Collection<Mumator> by lazy { loadMumators() }

val R.mumator: AnyMumator get() = AnyMumator

object AnyMumator {
    val aggressiveDeployment: Mumator by lazy { R.mumator("Aggressive Deployment") }
}

private val nameMumatorCache by lazy { R.mumators.associateBy { it.id } }

fun R.mumator(name: String): Mumator = mumatorOrNull(name)!!
fun R.mumatorOrNull(name: String): Mumator? = nameMumatorCache[name]