package com.dasoops.common.resources

import com.dasoops.common.util.languageAndControy
import java.util.Locale

actual fun getCurrentLanguage() = Language.getOrDefault(Locale.getDefault().languageAndControy)