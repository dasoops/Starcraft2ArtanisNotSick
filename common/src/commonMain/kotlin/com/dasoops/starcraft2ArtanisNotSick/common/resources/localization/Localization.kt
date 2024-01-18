package com.dasoops.starcraft2ArtanisNotSick.common.resources.localization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer
import net.mamoe.mirai.utils.map

@Serializable(with = Dict.Serializer::class)
open class Dict(
    protected val delegate: Map<String, String>
) : Map<String, String> by delegate {
    override fun get(key: String): String {
        return delegate[key] ?: "$$$key"
    }

    private object SuperSerializer :
        KSerializer<Map<String, String>> by MapSerializer(String.serializer(), String.serializer())

    open class DictSerializer<T : Dict>(
        constructor: (Map<String, String>) -> T
    ) : KSerializer<T> by SuperSerializer.map(
        resultantDescriptor = SuperSerializer.descriptor,
        serialize = { it.toMap() },
        deserialize = { constructor(it) },
    )

    internal object Serializer : DictSerializer<Dict>(constructor = { Dict(it) })
}

@Serializable
class Localization(
    val title: String,
    val simpleTitle: String,
    val screen: ScreenText,
    val ai: Dict,
    val dict: Dict,
    val race: RaceText,
) {
    @Transient
    lateinit var language: Language
}

@Serializable
data class ScreenText(
    val setting: SettingText,
    val mission: MissionText,
)