package com.dasoops.common.resources

import androidx.compose.runtime.Composable
import com.dasoops.common.resources.localization.str
import com.dasoops.common.util.BaseException
import com.dasoops.common.util.Serializer
import com.dasoops.common.util.StringDataEnum
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable

@Serializable
data class Ai(
    val id: String,
    val race: Race,
    val assaults: List<Assault>,
) {
    val name: String
        @Composable get() = R.str.ai[id]

    private val level2Units: Map<Int, List<Unit>> by lazy { assaults.associate { it.level to it.units } }
    fun units(level: Int): List<Unit> = level2Units[level]!!
}

@Serializable(with = Race.Serializer::class)
enum class Race(
    val value: String
) : StringDataEnum {
    Terran(value = "Terran"),
    Protoss(value = "Protoss"),
    Zerg(value = "Zerg"),
    ;

    override val data: String = value

    internal object Serializer : KSerializer<Race> by StringDataEnum.Serializer()
    companion object {
        val Default = Terran
    }
}

val Race.nameLocalization @Composable get() = R.str.race[this.value]

@Serializable
data class Assault(
    val level: Int,
    val units: List<Unit>,
)

private fun loadAi(): List<Ai> = R.resourceConfig<List<Ai>>("ai.json")
internal val R.ai: List<Ai> by lazy { loadAi() }

private val id2Ai: Map<String, Ai> by lazy { R.ai.associateBy { it.id } }
fun R.ai(id: String): Ai = id2Ai[id] ?: throw BaseException("undefined Ai[id=$id]")
private val race2Ai: Map<Race, List<Ai>> by lazy { R.ai.groupBy { it.race } }
fun R.ai(race: Race): List<Ai> = race2Ai[race] ?: throw BaseException("undefined Ai[race=$race]")