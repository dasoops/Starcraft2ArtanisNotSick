package com.dasoops.starcraft2ArtanisNotSick.common.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.dasoops.starcraft2ArtanisNotSick.common.component.theme.MyTheme
import com.dasoops.starcraft2ArtanisNotSick.common.content.mission.MissionScreen
import com.dasoops.starcraft2ArtanisNotSick.common.content.setting.SettingContent
import com.dasoops.starcraft2ArtanisNotSick.common.util.hoverChangeCursor

@Composable
fun RootContent(
    component: RootComponent,
) {
    val setting by component.setting.subscribeAsState()

    MyTheme(theme = setting.theme) {
        Surface(color = MaterialTheme.colorScheme.background) {
            Row {
                SideBar(component = component, modifier = Modifier)
                Children(component = component, modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun SideBar(
    component: RootComponent,
    modifier: Modifier = Modifier,
) {
    val childStack by component.childStack.subscribeAsState()
    val active = remember(childStack) { childStack.active.instance }
    val sortItems = remember { childStack.items.sortedBy { it.configuration.index } }

    NavigationRail(
        modifier = modifier.padding(end = 8.dp)
    ) {
        Column(
            Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val childCount = childStack.items.size

            sortItems.forEachIndexed { index, it ->
                val instance = it.instance

                NavigationRailItem(
                    selected = instance === active,
                    icon = {
                        Icon(imageVector = instance.icon, contentDescription = instance.title())
                    },
                    label = { Text(text = instance.title()) },
                    onClick = { component.changeScreen(it.configuration) },
                    modifier = Modifier.hoverChangeCursor()
                )
                if (index != childCount) Spacer(Modifier.height(24.dp))
            }
        }
    }
}


@Composable
private fun Children(
    component: RootComponent,
    modifier: Modifier = Modifier,
) {
    Children(
        stack = component.childStack,
        modifier = modifier,
        animation = stackAnimation(fade()),
    ) {
        when (val child = it.instance) {
            is RootComponent.Child.MissionChild -> MissionScreen(child.component)
            is RootComponent.Child.SettingChild -> SettingContent(child.component)
        }
    }
}