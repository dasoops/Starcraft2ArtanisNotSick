package com.dasoops.common.resources.mission.position

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/* EventPosition */
@Serializable
sealed interface EventPosition

@Serializable
sealed class AbstractEventPosition : EventPosition {
    override fun toString(): String {
        return "${javaClass.simpleName}[]"
    }
}

@SerialName(SinglePosition.SERIAL_NAME)
@Serializable
data class SinglePosition(
    @SerialName(value = "position") private val _position: String,
) : AbstractEventPosition() {
    val position: Position get() = position(this._position)

    companion object Key {
        const val SERIAL_NAME: String = "Single"
    }
}

@SerialName(MultiplePosition.SERIAL_NAME)
@Serializable
data class MultiplePosition(
    @SerialName(value = "position") private val _position: List<String>,
) : AbstractEventPosition() {
    val position get() = this._position.map { position(it) }

    companion object Key {
        const val SERIAL_NAME: String = "Multiple"
    }
}

@SerialName(RandomPosition.SERIAL_NAME)
@Serializable
data class RandomPosition(
    val position: List<WeightPosition>,
) : AbstractEventPosition() {

    @Serializable
    data class WeightPosition(
        val weight: Float,
        private val id: String,
    ) {
        val position get() = position(this.id)
    }

    companion object Key {
        const val SERIAL_NAME: String = "Random"
    }
}

@SerialName(UnknownPosition.SERIAL_NAME)
@Serializable
class UnknownPosition : AbstractEventPosition() {
    companion object Key {
        const val SERIAL_NAME: String = "Unknown"
    }
}

/* Position */
@Serializable
sealed interface Position {
    val id: String
}

@Serializable
sealed class AbstractPosition : Position {
    override fun toString(): String {
        return "${javaClass.simpleName}[id=$id]"
    }
}

@SerialName(PointPosition.SERIAL_NAME)
@Serializable
data class PointPosition(
    override val id: String,
) : AbstractPosition() {
    companion object Key {
        const val SERIAL_NAME: String = "Point"
    }
}

@SerialName(RangePosition.SERIAL_NAME)
@Serializable
data class RangePosition(
    override val id: String,
) : AbstractPosition() {
    companion object Key {
        const val SERIAL_NAME: String = "Range"
    }
}

