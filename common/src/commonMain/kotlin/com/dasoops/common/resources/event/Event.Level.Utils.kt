package com.dasoops.common.resources.event

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.dasoops.common.LocalState

val Level.text: String
    @Composable get() = run {
        val halfSuffix by LocalState.current.setting.halfLevelSuffix
        when (this) {
            is NormalLevel -> "T$level"
            is HalfStrengthLevel -> "T$level$halfSuffix"
            is FixedStrengthLevel -> "Fixed"
            is FixedTechLevel -> "Fixed"
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