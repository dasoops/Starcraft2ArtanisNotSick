package com.dasoops.common

import com.dasoops.common.resources.localization.Language

expect fun getCurrentLanguage(): Language

expect fun getPlatformName(): String

expect fun getDependencies(): Dependencies