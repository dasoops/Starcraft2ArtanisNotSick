package com.dasoops.common.resources.map.event

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
        if (mergeSameLevel && this.upgrade == this.cost) {
            this.upgrade.text
        } else {
            this.cost.text + " / " + this.upgrade.text
        }
    }