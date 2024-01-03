@file:JvmName("MissionKt")

package com.dasoops.common.resources.mission

import com.dasoops.common.resources.R

private val nameMissionCache by lazy { R.missions.associateBy { it.name } }

fun R.mission(name: String): Mission = missionOrNull(name)!!
fun R.missionOrNull(name: String): Mission? = nameMissionCache[name]