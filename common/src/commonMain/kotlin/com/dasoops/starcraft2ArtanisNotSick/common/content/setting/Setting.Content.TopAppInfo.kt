package com.dasoops.starcraft2ArtanisNotSick.common.content.setting

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.onClick
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dasoops.starcraft2ArtanisNotSick.common.resources.R
import com.dasoops.starcraft2ArtanisNotSick.common.resources.image
import com.dasoops.starcraft2ArtanisNotSick.common.resources.localization.str
import com.dasoops.starcraft2ArtanisNotSick.common.resources.version
import com.dasoops.starcraft2ArtanisNotSick.common.util.hoverChangeCursor

@Composable
internal fun TopAppInfo() {
    Row(Modifier.height(160.dp)) {
        Column {
            TitleAndVersion()
            Another()

            Spacer(Modifier.height(6.dp))

            Image(
                painter = R.image.main,
                contentDescription = "App icon image",
            )
        }
        Spacer(Modifier.width(32.dp))

        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
            githubRepository()
        }
    }
}

@Composable
private fun githubRepository() {
    val uriHandler = LocalUriHandler.current
    Row(modifier = Modifier.clickable { uriHandler.openUri("https://github.com/dasoops/Starcraft2ArtanisNotSick") }
        .padding(6.dp)) {
        Box(modifier = Modifier.align(Alignment.CenterVertically)) {
            Icon(
                painter = painterResource("/image/icon/github.svg"),
                contentDescription = "github icon",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
        Spacer(Modifier.width(6.dp))
        Column(
            modifier = Modifier.hoverChangeCursor(),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = R.str.screen.setting.github.name,
                style = MaterialTheme.typography.bodyLarge,
            )
            Text(
                text = R.str.screen.setting.github.sourceCode,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Left
            )
        }
    }
}

@Composable
private fun TitleAndVersion() {
    Row {
        Text(
            text = R.str.title,
            style = MaterialTheme.typography.titleLarge.copy(
                fontSize = 28.sp
            )
        )
        Spacer(Modifier.width(6.dp))

        Text(
            text = "(${R.version})",
            style = MaterialTheme.typography.titleSmall,
            lineHeight = MaterialTheme.typography.titleLarge.lineHeight
        )
    }
}

@Composable
@OptIn(ExperimentalFoundationApi::class)
private fun Another() {
    val uriHandler = LocalUriHandler.current
    Row {
        Text(
            text = " by ",
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = "Dasoops",
            style = MaterialTheme.typography.titleMedium.copy(
                color = Color(0xFF1BA1E2),
                textDecoration = TextDecoration.Underline
            ),
            modifier = Modifier
                .hoverChangeCursor()
                .onClick { uriHandler.openUri("Http://github.com/dasoops") }
        )
    }
}