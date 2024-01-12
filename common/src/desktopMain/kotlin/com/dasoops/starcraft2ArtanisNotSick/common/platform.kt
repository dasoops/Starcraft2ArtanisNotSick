package com.dasoops.starcraft2ArtanisNotSick.common

import com.dasoops.starcraft2ArtanisNotSick.common.resources.localization.Language
import com.dasoops.starcraft2ArtanisNotSick.common.util.languageAndControy
import java.util.Locale

actual fun getPlatformName() = "Desktop"

actual fun getCurrentLanguage() = Language.getOrDefault(Locale.getDefault().languageAndControy)