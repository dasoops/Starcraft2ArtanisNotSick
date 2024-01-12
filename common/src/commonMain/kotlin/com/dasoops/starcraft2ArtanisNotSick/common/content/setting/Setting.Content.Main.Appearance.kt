package com.dasoops.starcraft2ArtanisNotSick.common.content.setting

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.dasoops.starcraft2ArtanisNotSick.common.component.theme.Theme
import com.dasoops.starcraft2ArtanisNotSick.common.resources.R
import com.dasoops.starcraft2ArtanisNotSick.common.resources.localization.Language
import com.dasoops.starcraft2ArtanisNotSick.common.resources.localization.str

@Composable
internal fun Appearance(component: SettingComponent) {
    val model by component.state.subscribeAsState()
    Title(R.str.screen.setting.appearance.title)
    Language(model.language, component::onSelectLanguage)
    Theme(model.theme, component::onSelectTheme)
}

@Composable
private fun Language(value: Language, onSelectItem: (Language) -> Unit) {
    SelectOption(
        title = R.str.screen.setting.appearance.language.title,
        subTitle = R.str.screen.setting.appearance.language.subTitle,
        selectText = value.show,
        items = Language.values().toList(),
        itemText = { it.show },
        onSelectItem = onSelectItem
    )
}

@Composable
private fun Theme(value: Theme, onSelectItem: (Theme) -> Unit) {
    SelectOption(
        title = R.str.screen.setting.appearance.theme.subTitle,
        subTitle = R.str.screen.setting.appearance.theme.subTitle,
        selectText = value.show,
        items = Theme.values().toList(),
        itemText = { it.show },
        onSelectItem = onSelectItem
    )
}