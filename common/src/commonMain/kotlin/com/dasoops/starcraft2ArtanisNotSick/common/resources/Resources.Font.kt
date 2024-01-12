package com.dasoops.starcraft2ArtanisNotSick.common.resources

private const val suffix: String = "ttf"
private const val directory: String = "font"

interface FontResource {
    val kulimParkLight: String
    val kulimParkRegular: String
    val latoBold: String
    val latoRegular: String
    val consolas: String
    val consolasBold: String
    operator fun get(key: String): String
}

val Resources.font: FontResource by lazy {
    fun r(name: String) = "$directory/$name.$suffix"
    object : FontResource {
        override val kulimParkLight = r("kulim_park_light")
        override val kulimParkRegular = r("kulim_park_regular")
        override val latoBold = r("lato_bold")
        override val latoRegular = r("lato_regular")
        override val consolas = r("consolas")
        override val consolasBold = r("consolas_bold")

        override fun get(key: String) = "$key.$suffix"
    }
}