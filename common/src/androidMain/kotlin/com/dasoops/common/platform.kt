package com.dasoops.common

import com.dasoops.common.resources.localization.Language
import com.dasoops.common.util.languageAndControy
import java.util.Locale

actual fun getPlatformName() = "Android"

actual fun getCurrentLanguage() = Language.getOrDefault(Locale.getDefault().languageAndControy)

actual fun getDependencies() = object : Dependencies {
}