package com.dasoops.common.resources

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import com.dasoops.common.resources.localization.str
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.builtins.serializer
import net.mamoe.mirai.utils.map

private val idUnitCache: MutableMap<String, Unit> = hashMapOf()

fun R.unit(id: String): Unit = idUnitCache.computeIfAbsent(id) { Unit(id) }

@Serializable(with = Unit.Serializer::class)
class Unit(val id: String) {
    internal object Serializer : KSerializer<Unit> by String.serializer().map(
        serialize = { it.id },
        deserialize = { R.unit(it) }
    )
}

val Unit.name: String @Composable get() = R.str.dict[id]
val Unit.image: Painter @Composable get() = R.image.unit(this)