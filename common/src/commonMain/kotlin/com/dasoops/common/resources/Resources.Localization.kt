package com.dasoops.common.resources

val currentLanguage: Language by lazy { getCurrentLanguage() }
expect fun getCurrentLanguage(): Language

enum class Language(
    val value: String,
) {
    ZH_CN("zh_cn"),
    EN_US("en_us");
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
        R.resourceConfig<Map<String, String>>("localization/${currentLanguage.value}.json")
    object : Localization(currentLanguage, localizationMap) {}
}