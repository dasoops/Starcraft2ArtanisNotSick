package com.dasoops.common.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.dasoops.common.resources.R
import com.dasoops.common.resources.str
import com.dasoops.common.screen.home.HomeScreen
import com.dasoops.common.screen.setting.SettingScreen
import com.dasoops.common.util.Serializer
import com.dasoops.common.util.StringDataEnum
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable

val logger = KotlinLogging.logger { }

@Serializable(with = Screen.Companion.Serializer::class)
enum class Screen(
    override val data: String,
    val mainScreen: @Composable () -> Unit,
    val icon: ImageVector,
) : StringDataEnum {
    HOME(
        data = "Home",
        icon = Icons.Default.Home,
        mainScreen = { HomeScreen() }
    ) {
        override val text: String
            @Composable get() = R.str.home
    },
    SETTING(
        data = "Setting",
        icon = Icons.Default.Settings,
        mainScreen = { SettingScreen() }
    ) {
        override val text: String
            @Composable get() = R.str.setting
    },
    ;

    abstract val text: String @Composable get

    companion object {
        val Default = HOME

        object Serializer : KSerializer<Screen> by StringDataEnum.Serializer<Screen>()
    }
}

