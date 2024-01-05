package com.dasoops.common.resources

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.dasoops.common.logger
import com.dasoops.common.resources.mission.Mission
import com.dasoops.common.resources.mission.mission
import com.dasoops.common.screen.Screen
import com.dasoops.common.screen.setting.Setting
import com.dasoops.common.util.MutableStateSerializer
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
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
    val missionState: MissionState

    constructor(
        map: MissionState = MissionState.Default,
        screen: Screen = Screen.Default,
        setting: Setting = Setting.Default,
    ) {
        this.missionState = map
        this.screen = mutableStateOf(screen)
        this.setting = setting
    }

    companion object {
        val Default = AppState()
    }
}

@Serializable
data class MissionState(
    @Serializable(with = MutableStateSerializer::class)
    val current: MutableState<@Serializable(with = MissionSerializer::class) Mission?> = mutableStateOf(
        null
    ),
    @Transient
    val timer: MutableState<Int> = mutableStateOf(0),
    @Serializable(with = MutableStateSerializer::class)
    val showAggressiveDeploymentEvent: MutableState<Boolean> = mutableStateOf(false),
    @Transient
    val timerStart: MutableState<Boolean> = mutableStateOf(false),
    @Transient
    val autoScroll: MutableState<Boolean> = mutableStateOf(false),
    @Transient
    val showHide: MutableState<Boolean> = mutableStateOf(false),
    @Transient
    val openAiChooser: MutableState<Boolean> = mutableStateOf(false),
    @Transient
    val ai: MutableState<Ai?> = mutableStateOf(null),
) {

    fun clear(setting: Setting) {
        current.value = null
        timer.value = 0
        timerStart.value = false
        showAggressiveDeploymentEvent.value = false
        autoScroll.value = setting.autoScroll.value
        showHide.value = setting.showHide.value
        openAiChooser.value = false
        ai.value = null
    }

    companion object {
        object MissionSerializer : KSerializer<Mission> {
            override val descriptor: SerialDescriptor = Mission.serializer().descriptor
            override fun deserialize(decoder: Decoder) = R.mission(decoder.decodeString())
            override fun serialize(encoder: Encoder, value: Mission) =
                encoder.encodeString(value.name)
        }

        val Default: MissionState = MissionState()
    }
}

/* util */
private val json = Json { prettyPrint = true }
private val dataFile by lazy { R.data("appState.json") }

fun loadAppState(): AppState {
    val dataString = dataFile.readText()
    logger.info { "read state dataJson[${dataString}]" }
    return if (dataString.isBlank()) {
        AppState.Default
    } else {
        try {
            json.decodeFromString<AppState>(dataString)
        } catch (e: Exception) {
            logger.error(e) { "deserialize AppState failed, use default appState" }
            AppState.Default
        }
    }
}

fun saveAppState(appState: AppState) {
    val serializerAppStateString = json.encodeToString(appState)
    logger.trace { "onExit <- save state dataJson[$serializerAppStateString]" }
    dataFile.writeText(serializerAppStateString)
}