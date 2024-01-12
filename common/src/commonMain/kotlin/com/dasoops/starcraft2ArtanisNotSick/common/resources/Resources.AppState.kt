package com.dasoops.starcraft2ArtanisNotSick.common.resources

import com.arkivanov.essenty.parcelable.ParcelableContainer
import java.io.File
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

/* util */
private val savedFile by lazy { R.data(".data") }

fun R.loadAppState(): ParcelableContainer? = run {
    logger.info { "read state dataJson" }
    savedFile.takeIf(File::exists)?.let { file ->
        try {
            ObjectInputStream(file.inputStream()).use(ObjectInputStream::readObject) as ParcelableContainer
        } catch (e: Exception) {
            null
        }
    }
}

fun R.saveAppState(state: ParcelableContainer) {
    logger.info { "save state dataJson" }
    ObjectOutputStream(savedFile.outputStream()).use { output ->
        output.writeObject(state)
    }
}
