package com.dasoops.starcraft2ArtanisNotSick.common.resources.event

import com.dasoops.starcraft2ArtanisNotSick.common.resources.Unit
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventLevel(
    val tech: TechLevel,
    val strength: StrengthLevel,
)

sealed interface Level

@Serializable
sealed interface TechLevel : Level

@Serializable
sealed interface StrengthLevel : Level

@Serializable
@SerialName(value = NormalLevel.SERIAL_NAME)
data class NormalLevel(
    val level: Int,
) : TechLevel, StrengthLevel {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as NormalLevel

        return level == other.level
    }

    override fun hashCode(): Int {
        return level
    }

    companion object Key {
        const val SERIAL_NAME: String = "Normal"
    }
}

@Serializable
@SerialName(value = FixedStrengthLevel.SERIAL_NAME)
data class FixedStrengthLevel(
    val value: Int,
) : StrengthLevel {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FixedStrengthLevel

        return value == other.value
    }

    override fun hashCode(): Int {
        return value
    }

    companion object Key {
        const val SERIAL_NAME: String = "Fixed"
    }
}

@Serializable
@SerialName(value = FixedTechLevel.SERIAL_NAME)
data class FixedTechLevel(
    val units: List<Unit>,
) : TechLevel {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FixedTechLevel

        return units == other.units
    }

    override fun hashCode(): Int {
        return units.hashCode()
    }

    companion object Key {
        const val SERIAL_NAME: String = "Fixed"
    }
}

@Serializable
@SerialName(value = HalfStrengthLevel.SERIAL_NAME)
data class HalfStrengthLevel(
    val level: Int,
) : StrengthLevel {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as HalfStrengthLevel

        return level == other.level
    }

    override fun hashCode(): Int {
        return level
    }

    companion object Key {
        const val SERIAL_NAME: String = "Half"
    }
}