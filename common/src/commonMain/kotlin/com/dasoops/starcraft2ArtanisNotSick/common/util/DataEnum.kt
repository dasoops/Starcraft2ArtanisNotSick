package com.dasoops.starcraft2ArtanisNotSick.common.util

import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.serializer
import net.mamoe.mirai.utils.map

interface DataEnum<T> {
    val data: T

    companion object
}

@Suppress("FunctionName")
inline fun <V, reified T : DataEnum<V>> DataEnum.Companion.Serializer(
    superSerializer: KSerializer<V>,
    `class`: Class<T> = T::class.java
): KSerializer<T> {
    val enumMap = `class`.enumConstants.associateBy { it.data }

    return superSerializer.map(
        superSerializer.descriptor,
        serialize = { it.data },
        deserialize = { enumMap[it]!! }
    )
}

interface IntDataEnum : DataEnum<Int> {
    companion object
}

interface StringDataEnum : DataEnum<String> {
    companion object
}

@Suppress("FunctionName")
inline fun <reified T : DataEnum<Int>> IntDataEnum.Companion.Serializer(): KSerializer<T> =
    DataEnum.Serializer(Int.serializer())

@Suppress("FunctionName")
inline fun <reified T : DataEnum<String>> StringDataEnum.Companion.Serializer(): KSerializer<T> =
    DataEnum.Serializer(String.serializer())

inline fun <reified V, reified T> DataEnum.Companion.valueMap(): Map<V, T> where T : Enum<T>, T : DataEnum<V> =
    enumValues<T>().associateBy { it.data }
