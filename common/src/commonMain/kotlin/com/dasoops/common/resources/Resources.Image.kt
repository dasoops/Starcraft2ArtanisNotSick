package com.dasoops.common.resources

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import com.dasoops.common.resources.mission.Mission
import java.io.File

val R.image: Image
    get() = Image

object Image {
    private const val directoryDir: String = "image"
    private val directory = File(directoryDir)
    val main @Composable get() = image("main.jfif")
    val icon @Composable get() = image("main-icon.jpg")

    internal fun imagePath(fileName: String): String = "$directoryDir/$fileName"

    @Composable
    internal fun image(fileName: String): Painter = painterResource(imagePath(fileName))
}

@Composable
fun Image.mission(mission: Mission): Painter = image("missions/${mission.name}.jpg")

@Composable
fun Image.unit(unit: Unit): Painter = image("units/${unit.id}.png")