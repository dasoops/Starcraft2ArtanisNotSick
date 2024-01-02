package com.dasoops.common.resources.map

import com.dasoops.common.resources.R

private val nameMapCache by lazy { R.maps.associateBy { it.name } }

fun R.map(name: String): Map = mapOrNull(name)!!
fun R.mapOrNull(name: String): Map? = nameMapCache[name]