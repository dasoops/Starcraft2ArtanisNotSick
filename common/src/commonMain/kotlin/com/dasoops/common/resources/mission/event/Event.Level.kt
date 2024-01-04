package com.dasoops.common.resources.mission.event

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventLevel(
    val tech: TechLevel,
    val strength: StrengthLevel,
)

sealed interface Level {
    val value: Int
}

@Serializable
sealed class AbstractLevel : Level {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Level
        return value == other.value
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + value
        return result
    }
}

@Serializable
sealed interface TechLevel : Level

@Serializable
sealed interface StrengthLevel : Level

@Serializable
@SerialName(value = NormalLevel.SERIAL_NAME)
data class NormalLevel(
    override val value: Int,
) : AbstractLevel(), TechLevel, StrengthLevel {

    companion object Key {
        const val SERIAL_NAME: String = "Normal"
    }
}

@Serializable
@SerialName(value = HalfLevel.SERIAL_NAME)
data class HalfLevel(
    override val value: Int,
) : AbstractLevel(), StrengthLevel {

    companion object Key {
        const val SERIAL_NAME: String = "Half"
    }
}