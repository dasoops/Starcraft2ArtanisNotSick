package com.dasoops.starcraft2ArtanisNotSick.common.resources.localization

import kotlinx.serialization.Serializable

@Serializable(with = RaceText.Serializer::class)
class RaceText(
    delegate: Map<String, String>
) : Dict(delegate) {
    val terran: String by super.delegate
    val protoss: String by super.delegate
    val zerg: String by super.delegate

    internal object Serializer : DictSerializer<RaceText>(constructor = { RaceText(it) })
}