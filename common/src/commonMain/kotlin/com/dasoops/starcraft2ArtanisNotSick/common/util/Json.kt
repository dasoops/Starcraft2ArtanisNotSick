package com.dasoops.starcraft2ArtanisNotSick.common.util

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encodeToString
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json

inline fun <reified T> T.toJsonString(json: Json = Json.Default) = json.encodeToString(this)

inline fun <reified T> String.parseJson(json: Json = Json.Default) = json.decodeFromString<T>(this)

class MutableStateSerializer<T : Any>(
    private val dataSerializer: KSerializer<T>
) :
    KSerializer<MutableState<T>> {
    override val descriptor: SerialDescriptor = dataSerializer.descriptor

    override fun deserialize(decoder: Decoder): MutableState<T> =
        mutableStateOf(dataSerializer.deserialize(decoder))

    override fun serialize(encoder: Encoder, value: MutableState<T>) =
        dataSerializer.serialize(encoder, value.value)
}