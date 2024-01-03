package com.dasoops.common.resources

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.dasoops.common.LocalState
import com.dasoops.common.util.DataEnum
import com.dasoops.common.util.Serializer
import com.dasoops.common.util.StringDataEnum
import com.dasoops.common.util.valueMap
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable

val R.currentSystemLanguage: Language by lazy { getCurrentLanguage() }
expect fun getCurrentLanguage(): Language

@Serializable(with = Language.Serializer::class)
enum class Language(
    val value: String,
    val show: String,
) : StringDataEnum {
    ZH_CN("zh_cn", "简体中文"),
    EN_US("en_us", "English"),
    ;

    override val data: String = value

    object Serializer : KSerializer<Language> by StringDataEnum.Serializer()
    companion object {
        val Default: Language = EN_US
        val valueMap: Map<String, Language> = DataEnum.valueMap<String, Language>()
        fun getOrDefault(value: String, default: Language = Default) =
            valueMap.getOrDefault(value, default)
    }
}

abstract class Localization(
    val language: Language,
    delegate: Map<String, String>
) {
    val home: String by delegate
    val back: String by delegate
    val setting: String by delegate
    val title: String by delegate
    val simpleTitle: String by delegate
}

lateinit var localization: Localization
val Resources.str: Localization
    @Composable get() {
        val language by LocalState.current.setting.language
        if (!::localization.isInitialized) {
            val localizationMap: Map<String, String> =
                R.resourceConfig("localization/${language.value}.json")
            localization = object : Localization(currentSystemLanguage, localizationMap) {}
        }
        return localization
    }