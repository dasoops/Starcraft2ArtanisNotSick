package com.dasoops.common.screen.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dasoops.common.screen.setting.option.Appearance
import com.dasoops.common.screen.setting.option.MapInfo
import io.github.oshai.kotlinlogging.KotlinLogging

val logger = KotlinLogging.logger {}

@Composable
fun SettingScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 24.dp, start = 12.dp, end = 12.dp)
            .verticalScroll(rememberScrollState())
    ) {
        TopAppInfo()

        Divider(thickness = 1.dp, modifier = Modifier.padding(vertical = 16.dp))

        Appearance()
        MapInfo()
    }
}