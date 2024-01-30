package com.dasoops.starcraft2ArtanisNotSick.common.resources

private const val directory = "image/icon"
private fun svg(name: String) = "$directory/$name.svg"
private fun png(name: String) = "$directory/$name.png"

val R.icon: Icon get() = Icon

object Icon {
    val github by lazy { svg("github") }
    val keyboardArrowRight by lazy { svg("keyboard_arrow_right") }
    val keyboardArrowDown by lazy { svg("keyboard_arrow_down") }
    val minerals by lazy { png("minerals") }
    val vespene by lazy { png("vespene") }
}
