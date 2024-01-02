package com.dasoops.common.resources.map

import com.dasoops.common.resources.R
import com.dasoops.common.resources.resourceConfig

fun loadMaps(): Collection<Map> =
    R.resourceConfig<Collection<Map>>("map.json")

val R.maps: Collection<Map> by lazy { loadMaps() }
