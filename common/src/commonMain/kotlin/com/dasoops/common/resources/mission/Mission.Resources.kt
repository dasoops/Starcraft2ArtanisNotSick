package com.dasoops.common.resources.mission

import com.dasoops.common.resources.R
import com.dasoops.common.resources.resourceConfig

fun loadMissionss(): Collection<Mission> =
    R.resourceConfig<Collection<Mission>>("mission.json")

val R.missions: Collection<Mission> by lazy { loadMissionss() }
