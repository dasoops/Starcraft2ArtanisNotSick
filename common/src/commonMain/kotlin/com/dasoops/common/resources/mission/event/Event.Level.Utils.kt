package com.dasoops.common.resources.mission.event

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.dasoops.common.LocalState

val Level.text: String
    @Composable get() = run {
        val halfSuffix by LocalState.current.setting.halfLevelSuffix
        when (this) {
            is NormalLevel -> "T$value"
            is HalfLevel -> "T$value$halfSuffix"
        }
    }

val EventLevel.text: String
    @Composable get() = run {
        val mergeSameLevel by LocalState.current.setting.mergeSameLevel
        if (mergeSameLevel && this.tech == this.strength) {
            this.tech.text
        } else {
            this.strength.text + " / " + this.tech.text
        }
    }