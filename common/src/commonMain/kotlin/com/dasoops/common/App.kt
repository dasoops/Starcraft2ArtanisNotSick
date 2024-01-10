package com.dasoops.common

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import com.dasoops.common.component.SideBar
import com.dasoops.common.component.theme.MyTheme
import com.dasoops.common.resources.AppState
import com.dasoops.common.resources.loadAppState
import com.dasoops.common.resources.saveAppState
import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger { }

val LocalState = staticCompositionLocalOf { AppState.Default }
val LocalDependency = staticCompositionLocalOf { getDependencies() }

@Composable
fun app() {
    logger.info { "platform: ${getPlatformName()}" }
    val state: AppState = remember { loadAppState() }

    CompositionLocalProvider(
        LocalState provides state,
    ) {
        var screen by remember { state.screen }

        MyTheme(theme = state.settingState.theme.value) {
            Surface(color = MaterialTheme.colorScheme.background) {
                Row {
                    SideBar(screen = screen, onScreenChange = { screen = it })
                    screen.mainScreen()
                }
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            saveAppState(state)
        }
    }
}