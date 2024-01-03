package com.dasoops.common.resources

import com.dasoops.common.util.DataEnum
import com.dasoops.common.util.Serializer
import com.dasoops.common.util.StringDataEnum
import com.dasoops.common.util.valueMap
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable

val currentLanguage: Language by lazy { getCurrentLanguage() }
expect fun getCurrentLanguage(): Language

@Serializable(with = Language.Serializer::class)
enum class Language(
    val value: String
) : StringDataEnum {
    ZH_CN("zh_cn"),
    EN_US("en_us"),
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

val Resources.str: Localization by lazy {
    val localizationMap =
        R.resourceConfig<Map<String, String>>("localization/${currentLanguage.data}.json")
    object : Localization(currentLanguage, localizationMap) {}
}