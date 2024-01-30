package com.dasoops.starcraft2ArtanisNotSick.common.component

import androidx.compose.ui.res.useResource
import com.dasoops.starcraft2ArtanisNotSick.common.resources.R
import com.dasoops.starcraft2ArtanisNotSick.common.resources.sound
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import java.io.BufferedInputStream
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.Clip

object HintSound {
    private lateinit var clip: Clip

    suspend fun start() = coroutineScope {
        if (!::clip.isInitialized) {
            clip = AudioSystem.getClip()
            launch {
                useResource(R.sound.hint) {
                    clip.open(
                        AudioSystem.getAudioInputStream(BufferedInputStream(it))
                    )
                }
            }.join()
        }
        clip.framePosition = 0
        clip.start()
    }
}
