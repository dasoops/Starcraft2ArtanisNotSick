package com.dasoops.common.resources

expect fun getPlatformName(): String

val R.platform by lazy { getPlatformName() }