package com.dasoops.common.screen.setting

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dasoops.common.screen.setting.option.Appearance
import com.dasoops.common.screen.setting.option.MissionInfo
import io.github.oshai.kotlinlogging.KotlinLogging

val logger = KotlinLogging.logger {}

@Composable
fun SettingScreen() {
    val scrollState = rememberScrollState()
    Box(
        modifier = Modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, start = 12.dp, end = 24.dp)
                .verticalScroll(scrollState)
        ) {
            TopAppInfo()

            Divider(thickness = 1.dp, modifier = Modifier.padding(vertical = 16.dp))

            Appearance()
            MissionInfo()
        }
        VerticalScrollbar(
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .fillMaxHeight()
                .padding(top = 24.dp, end = 8.dp),
            adapter = rememberScrollbarAdapter(scrollState),
        )
    }

}