package com.dasoops.common.screen.setting.option

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.dasoops.common.component.theme.Theme
import com.dasoops.common.resources.R
import com.dasoops.common.resources.localization.Language
import com.dasoops.common.resources.localization.str
import com.dasoops.common.screen.setting.logger

@Composable
fun Appearance() {
    Title(R.str.screen.setting.appearance.title)
    Language()
    Theme()
}

@Composable
private fun Language() {
    var language by setting.language
    SelectOption(
        title = R.str.screen.setting.appearance.language.title,
        subTitle = R.str.screen.setting.appearance.language.subTitle,
        selectText = language.show,
        items = Language.values().toList(),
        itemText = { it.show },
        onSelectItem = {
            logger.debug { "setting.language change -> $it" }
            language = it
        }
    )
}

@Composable
private fun Theme() {
    var theme by setting.theme
    SelectOption(
        title = R.str.screen.setting.appearance.theme.subTitle,
        subTitle = R.str.screen.setting.appearance.theme.subTitle,
        selectText = theme.data,
        items = Theme.values().toList(),
        itemText = { it.data },
        onSelectItem = {
            logger.debug { "setting.theme change -> $it" }
            theme = it
        }
    )
}