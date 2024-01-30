package com.dasoops.starcraft2ArtanisNotSick.common.resources

private const val directory = "sound"
private const val extension = "wav"
private fun wav(name: String) = "$directory/$name.$extension"

val R.sound: Sound get() = Sound

object Sound {
    val hint by lazy { wav("hint") }
}
