package com.dasoops.starcraft2ArtanisNotSick.common.resources.localization

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.arkivanov.decompose.value.MutableValue
import com.dasoops.starcraft2ArtanisNotSick.common.resources.R
import com.dasoops.starcraft2ArtanisNotSick.common.resources.Resources
import com.dasoops.starcraft2ArtanisNotSick.common.resources.resourceConfig
import com.dasoops.starcraft2ArtanisNotSick.common.util.DataEnum
import com.dasoops.starcraft2ArtanisNotSick.common.util.Serializer
import com.dasoops.starcraft2ArtanisNotSick.common.util.StringDataEnum
import com.dasoops.starcraft2ArtanisNotSick.common.util.valueMap
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable

@Serializable(with = Language.Serializer::class)
enum class Language(
    val value: String,
    val show: String,
) : StringDataEnum {
    ZH_CN("zh_CN", "简体中文"),
    EN_US("en_US", "English"),
    ;

    override val data: String = value

    val localization: Localization by lazy {
        R.resourceConfig<Localization>("localization/${this.value}.json").apply {
            this.language = this@Language
        }
    }

    internal object Serializer : KSerializer<Language> by StringDataEnum.Serializer()
    companion object {
        val Default: Language = EN_US
        val valueMap: Map<String, Language> = DataEnum.valueMap<String, Language>()
        fun getOrDefault(value: String, default: Language = Default) =
            valueMap.getOrDefault(value, default)
    }
}

val R.currentSystemLanguage: Language by lazy { com.dasoops.starcraft2ArtanisNotSick.common.getCurrentLanguage() }
val R.currentLanguage: MutableValue<Language> by lazy { MutableValue(R.currentSystemLanguage) }

fun Resources.str(language: Language): Localization = language.localization

val Resources.str: Localization
    @Composable get() {
        val language by R.currentLanguage.subscribeAsState()
        return language.localization
    }