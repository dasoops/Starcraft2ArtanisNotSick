package com.dasoops.common.resources.localization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import net.mamoe.mirai.utils.map

@Serializable(with = Dict.Serializer::class)
class Dict(
    private val delegate: Map<String, String>
) : Map<String, String> by delegate {
    override fun get(key: String): String {
        return delegate[key] ?: "$$$key"
    }

    private object SuperSerializer :
        KSerializer<Map<String, String>> by MapSerializer(String.serializer(), String.serializer())

    internal object Serializer : KSerializer<Dict> by SuperSerializer.map(
        resultantDescriptor = SuperSerializer.descriptor,
        serialize = { it.toMap() },
        deserialize = { Dict(it) },
    )
}

@Serializable
class Localization(
    val title: String,
    val simpleTitle: String,
    val screen: ScreenText,
    val dict: Dict,
) {
    @Transient
    lateinit var language: Language
}

@Serializable
data class ScreenText(
    val setting: SettingText,
    val mission: MissionText,
)