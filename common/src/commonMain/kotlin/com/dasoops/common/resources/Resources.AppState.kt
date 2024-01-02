package com.dasoops.common.resources

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.dasoops.common.logger
import com.dasoops.common.resources.map.Map
import com.dasoops.common.resources.map.map
import com.dasoops.common.screen.Screen
import com.dasoops.common.screen.setting.Setting
import com.dasoops.common.util.MutableStateSerializer
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.Transient
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encodeToString
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.Json

/* define */
@Serializable
class AppState {
    @Serializable(with = MutableStateSerializer::class)
    val screen: MutableState<Screen>
    val setting: Setting
    val mapState: MapState

    constructor(
        map: MapState = MapState.Default,
        screen: Screen = Screen.Default,
        setting: Setting = Setting.Default,
    ) {
        this.mapState = map
        this.screen = mutableStateOf(screen)
        this.setting = setting
    }

    companion object {
        val Default = AppState()
    }
}

@Serializable
data class MapState(
    @Serializable(with = MutableStateSerializer::class)
    val current: MutableState<@Serializable(with = MapSerializer::class) Map?> = mutableStateOf(null),
    @Serializable(with = MutableStateSerializer::class)
    val showAggressiveDeploymentEvent: MutableState<Boolean?> = mutableStateOf(false),
    @Transient
    val timer: MutableState<Int> = mutableStateOf(0),
    @Transient
    val timerStart: MutableState<Boolean> = mutableStateOf(false),
) {

    fun clear() {
        current.value = null
        showAggressiveDeploymentEvent.value = false
        timer.value = 0
        timerStart.value = false
    }

    companion object {
        object MapSerializer : KSerializer<Map> {
            override val descriptor: SerialDescriptor = Map.serializer().descriptor
            override fun deserialize(decoder: Decoder) = R.map(decoder.decodeString())
            override fun serialize(encoder: Encoder, value: Map) = encoder.encodeString(value.name)
        }

        val Default: MapState = MapState()
    }
}

/* util */
private val dataFile by lazy { R.data("appState.json") }

fun loadAppState(): AppState {
    val dataString = dataFile.readText()
    return if (dataString.isBlank()) {
        AppState.Default
    } else {
        try {
            Json.decodeFromString<AppState>(dataString)
        } catch (e: SerializationException) {
            logger.error(e) { "deserialize AppState failed, use default appState" }
            AppState.Default
        }
    }
}

private val json = Json { prettyPrint = true }

fun saveAppState(appState: AppState) {
    val serializerAppStateString = json.encodeToString(appState)
    logger.trace { "onExit <- save state dataJson[$serializerAppStateString]" }
    dataFile.writeText(serializerAppStateString)
}