package com.dasoops.starcraft2ArtanisNotSick.common.util

fun Char.repeat(count: Int): String = StringBuilder().run {
    (0 until count).forEach { _ ->
        append(this@repeat)
    }
    toString()
}

fun String.fillOutOfLength(
    maxLength: Int,
    fillMaxLength: Int = 3,
    fillChar: Char = '.',
) = run {
    if (this.length < (maxLength - fillMaxLength)) {
        this
    } else if (this.length < maxLength) {
        this + fillChar.repeat(maxLength - this.length + 1)
    } else {
        this.substring(0..(maxLength - fillMaxLength)) + fillChar.repeat(fillMaxLength)
    }
}