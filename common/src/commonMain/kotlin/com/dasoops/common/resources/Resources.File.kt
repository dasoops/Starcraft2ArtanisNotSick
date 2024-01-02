package com.dasoops.common.resources

import androidx.compose.ui.res.useResource
import kotlinx.serialization.json.Json
import java.io.File

val R.userDir: File by lazy { File(System.getProperty("user.dir")) }

/* data */
private val R.dataDirFile: File by lazy { File("${R.userDir}/data").apply { if (!exists()) mkdirs() } }

fun R.data(name: String): File = dataDirFile.resolve(name).apply {
    if (!exists()) {
        logger.info { "undefined dataFile[$name], create empty" }
        createNewFile()
    }
}

fun R.dataString(name: String) = data(name).readText(Charsets.UTF_8)

/* config */
@PublishedApi
internal const val configDirectory: String = "config"

fun R.resourceString(name: String) =
    useResource(name) { it.readAllBytes().toString(Charsets.UTF_8) }

inline fun <reified T> R.resourceConfig(name: String): T =
    Json.decodeFromString<T>(
        resourceString("$configDirectory/$name").apply {
            logger.info { "load config[$name]: [length=${this.length}]" }
        }
    )