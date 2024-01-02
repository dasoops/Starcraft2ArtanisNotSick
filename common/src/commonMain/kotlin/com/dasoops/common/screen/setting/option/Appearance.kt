package com.dasoops.common.screen.setting.option

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dasoops.common.component.theme.Theme
import com.dasoops.common.screen.setting.logger

@Composable
fun Appearance() {
    Title("Appearance")
    Theme()
}

@Composable
private fun Theme() {
    var theme by setting.theme
    Row {
        Description(
            modifier = Modifier,
            title = "Theme",
            subTitle = "Select app color scheme",
        )
        Box(
            modifier = Modifier,
            contentAlignment = Alignment.CenterStart
        ) {
            Select(
                text = { Text(text = theme.name, style = MaterialTheme.typography.labelMedium) },
                modifier = Modifier.size(height = 30.dp, width = 160.dp),
            ) { (_, setExpanded) ->
                Theme.values().forEach {
                    DropdownMenuItem(
                        text = { Text(text = it.data) },
                        onClick = {
                            logger.debug { "setting.theme change -> $it" }
                            theme = it
                            setExpanded(false)
                        }
                    )
                }
            }
        }
    }
}