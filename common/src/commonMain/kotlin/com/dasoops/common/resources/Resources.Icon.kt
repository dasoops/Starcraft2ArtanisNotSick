package com.dasoops.common.resources

private const val directory = "image/icon"
private const val extension = "svg"
private fun svg(name: String) = "$directory/$name.$extension"

val R.icon: Icon get() = Icon

object Icon {
    val github by lazy { svg("github") }
    val keyboardArrowRight by lazy { svg("keyboard_arrow_right") }
    val keyboardArrowDown by lazy { svg("keyboard_arrow_down") }
}
