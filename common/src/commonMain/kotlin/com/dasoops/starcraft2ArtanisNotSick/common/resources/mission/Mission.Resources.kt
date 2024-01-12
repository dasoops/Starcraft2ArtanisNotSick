package com.dasoops.starcraft2ArtanisNotSick.common.resources.mission

import com.dasoops.starcraft2ArtanisNotSick.common.resources.R
import com.dasoops.starcraft2ArtanisNotSick.common.resources.resourceConfig

private fun loadMissions(): Collection<Mission> =
    R.resourceConfig<Collection<Mission>>("mission.json")

val R.missions: Collection<Mission> by lazy { loadMissions() }
