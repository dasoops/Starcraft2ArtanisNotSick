package com.dasoops.common.resources

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.dasoops.common.resources.mission.Mission
import com.dasoops.common.resources.mission.mission
import com.dasoops.common.screen.Screen
import com.dasoops.common.screen.mission.LocalMissionStateModel
import com.dasoops.common.screen.setting.SettingState
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
    val settingState: SettingState
    val missionState: MissionState

    @Transient
    val localMissionStateModel: LocalMissionStateModel = LocalMissionStateModel()

    constructor(
        map: MissionState = MissionState.Default,
        screen: Screen = Screen.Default,
        settingState: SettingState = SettingState.Default,
    ) {
        this.missionState = map
        this.screen = mutableStateOf(screen)
        this.settingState = settingState
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
) {
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