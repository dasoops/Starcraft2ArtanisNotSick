package com.dasoops.common.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dasoops.common.screen.Screen
import com.dasoops.common.screen.logger
import com.dasoops.common.util.hoverChangeCursor

@Composable
fun SideBar(screen: Screen, onScreenChange: (Screen) -> Unit) {
    NavigationRail(
        modifier = Modifier.padding(end = 8.dp)
    ) {
        Column(
            Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val screenArray = Screen.values()
            val screenCount = screenArray.size
            screenArray.forEachIndexed { index, it ->
                NavigationRailItem(
                    selected = it == screen,
                    icon = { Icon(imageVector = it.icon, contentDescription = it.text) },
                    label = { Text(text = it.text) },
                    onClick = {
                        logger.trace { "screen change -> $it" }
                        onScreenChange(it)
                    },
                    modifier = Modifier.hoverChangeCursor()
                )
                if (index != screenCount) Spacer(Modifier.height(24.dp))
            }
        }
    }
}
