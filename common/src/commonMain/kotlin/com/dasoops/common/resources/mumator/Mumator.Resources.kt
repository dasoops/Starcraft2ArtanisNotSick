package com.dasoops.common.resources.mumator

import com.dasoops.common.resources.R
import com.dasoops.common.resources.resourceConfig

private fun loadMumators(): Collection<Mumator> =
    R.resourceConfig<Collection<Mumator>>("mumator.json")

internal val R.mumators: Collection<Mumator> by lazy { loadMumators() }

val R.mumator: AnyMumator get() = AnyMumator

object AnyMumator {
    val aggressiveDeployment: Mumator by lazy { R.mumator("Aggressive Deployment") }
    val voidRifts: Mumator by lazy { R.mumator("Void Rifts") }
}

private val idMumatorCache by lazy { R.mumators.associateBy { it.id } }

fun R.mumator(id: String): Mumator = mumatorOrNull(id)!!
fun R.mumatorOrNull(name: String): Mumator? = idMumatorCache[name]