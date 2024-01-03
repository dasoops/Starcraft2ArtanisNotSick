package com.dasoops.common.util

import java.util.Locale

val Locale.languageAndControy get() = "${this.language}_${this.country}"