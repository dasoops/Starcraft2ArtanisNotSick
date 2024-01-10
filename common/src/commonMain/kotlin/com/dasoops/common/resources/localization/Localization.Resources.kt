package com.dasoops.common.resources.localization

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.dasoops.common.LocalState
import com.dasoops.common.getCurrentLanguage
import com.dasoops.common.resources.R
import com.dasoops.common.resources.Resources
import com.dasoops.common.resources.resourceConfig
import com.dasoops.common.util.DataEnum
import com.dasoops.common.util.Serializer
import com.dasoops.common.util.StringDataEnum
import com.dasoops.common.util.valueMap
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable

val R.currentSystemLanguage: Language by lazy { getCurrentLanguage() }

@Serializable(with = Language.Serializer::class)
enum class Language(
    val value: String,
    val show: String,
) : StringDataEnum {
    ZH_CN("zh_CN", "简体中文"),
    EN_US("en_US", "English"),
    ;

    override val data: String = value

    internal object Serializer : KSerializer<Language> by StringDataEnum.Serializer()
    companion object {
        val Default: Language = EN_US
        val valueMap: Map<String, Language> = DataEnum.valueMap<String, Language>()
        fun getOrDefault(value: String, default: Language = Default) =
            valueMap.getOrDefault(value, default)
    }
}

lateinit var localization: Localization

val Resources.str: Localization
    @Composable get() {
        val appState = LocalState.current
        val language by remember { appState.settingState.language }
        if (!::localization.isInitialized || localization.language != language) {
            localization = R.resourceConfig("localization/${language.value}.json")
            localization.language = language
        }
        return localization
    }