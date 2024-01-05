package com.dasoops.common.util

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable

interface BaseTime {
    val originSeconds: Int
    val hour get() = originSeconds / 60 / 60
    operator fun component1() = hour
    val minute get() = originSeconds % 3600 / 60
    operator fun component2() = minute
    val second get() = originSeconds % 60
    operator fun component3() = second
}

interface UnitTime : BaseTime {
    val value: Int
    val unit: TimeUnit
    override val originSeconds: Int get() = unit.convertToSeconds(value)

    @Serializable
    open class DefaultImpl(
        override val value: Int,
        override val unit: TimeUnit,
    ) : UnitTime
}

fun BaseTime.fillZero(value: Int) = if (value >= 10) "$value" else "0$value"

val BaseTime.text: String
    get() {
        val (_, minute, second) = this
        return "${fillZero(minute)}:${fillZero(second)}"
    }


@Serializable(with = TimeUnit.Serializer::class)
enum class TimeUnit(
    override val data: String,
    val convertToSeconds: (Int) -> Int
) : StringDataEnum {
    MILLISECOND(data = "Millisecond", convertToSeconds = { it / 1000 }),
    SECOND(data = "Second", convertToSeconds = { it }),
    MINUTE(data = "Minute", convertToSeconds = { it * 60 }),
    HOUR(data = "Hour", convertToSeconds = { it * 60 * 60 }),
    ;

    internal object Serializer : KSerializer<TimeUnit> by StringDataEnum.Serializer<TimeUnit>()
}

fun TimeUnit.convertToMilloseconds(origin: Int) = this.convertToSeconds(origin) * 1000