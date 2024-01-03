package com.dasoops.common.screen.setting.option

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.dasoops.common.component.theme.Theme
import com.dasoops.common.screen.setting.logger

@Composable
fun Appearance() {
    Title("Appearance")
    Theme()
}

@Composable
private fun Theme() {
    var theme by setting.theme
    SelectOption(
        title = "Theme",
        subTitle = "Select app color scheme",
        selectText = theme.data,
        items = Theme.values().toList(),
        itemText = { it.data },
        onSelectItem = {
            logger.debug { "setting.theme change -> $it" }
            theme = it
        }
    )
}