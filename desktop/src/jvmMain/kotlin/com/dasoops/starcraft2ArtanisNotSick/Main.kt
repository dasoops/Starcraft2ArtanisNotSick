package com.dasoops.starcraft2ArtanisNotSick

import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetbrains.lifecycle.LifecycleController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.arkivanov.essenty.statekeeper.StateKeeperDispatcher
import com.badoo.reaktive.coroutinesinterop.asScheduler
import com.badoo.reaktive.scheduler.overrideSchedulers
import com.dasoops.starcraft2ArtanisNotSick.common.content.RootComponent
import com.dasoops.starcraft2ArtanisNotSick.common.content.RootContent
import com.dasoops.starcraft2ArtanisNotSick.common.getPlatformName
import com.dasoops.starcraft2ArtanisNotSick.common.resources.R
import com.dasoops.starcraft2ArtanisNotSick.common.resources.image
import com.dasoops.starcraft2ArtanisNotSick.common.resources.loadAppState
import com.dasoops.starcraft2ArtanisNotSick.common.resources.localization.str
import com.dasoops.starcraft2ArtanisNotSick.common.resources.saveAppState
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi

private val logger = KotlinLogging.logger {}

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalDecomposeApi::class)
fun main() {
    logger.info { "platform: ${getPlatformName()}" }

    overrideSchedulers(main = Dispatchers.Main::asScheduler)

    val lifecycle = LifecycleRegistry()
    val stateKeeper = StateKeeperDispatcher(R.loadAppState())

    val root = runOnUiThread {
        RootComponent.DefaultImpl(
            componentContext = DefaultComponentContext(
                lifecycle = lifecycle,
                stateKeeper = stateKeeper,
            )
        )
    }

    application {
        val windowState = rememberWindowState(
            placement = WindowPlacement.Floating,
            position = WindowPosition.Aligned(Alignment.Center),
            size = DpSize(1024.dp, 768.dp),
        )

        LifecycleController(lifecycle, windowState)

        Window(
            onCloseRequest = {
                R.saveAppState(stateKeeper.save())
                this.exitApplication()
            },
            icon = R.image.icon,
            title = R.str.title,
            resizable = true,
            state = windowState,
        ) {
            RootContent(
                component = root
            )
        }
    }
}