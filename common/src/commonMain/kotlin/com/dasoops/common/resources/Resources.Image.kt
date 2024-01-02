package com.dasoops.common.resources

private const val directory: String = "image"

val R.image: Image
    get() = Image

object Image {
    val main by lazy { "$directory/main.jfif" }
    val icon by lazy { "$directory/main-icon.jpg" }
}
