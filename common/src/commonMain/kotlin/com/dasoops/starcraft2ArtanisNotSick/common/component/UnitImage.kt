package com.dasoops.starcraft2ArtanisNotSick.common.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.TooltipArea
import androidx.compose.foundation.TooltipPlacement
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.dasoops.starcraft2ArtanisNotSick.common.resources.image
import com.dasoops.starcraft2ArtanisNotSick.common.resources.name

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UnitImage(it: com.dasoops.starcraft2ArtanisNotSick.common.resources.Unit) {
    TooltipArea(
        tooltipPlacement = TooltipPlacement.ComponentRect(
            alignment = Alignment.TopEnd,
            offset = DpOffset(0.dp, (-30).dp)
        ),
        tooltip = {
            Text(
                text = it.name,
            )
        }) {
        Image(
            painter = it.image,
            contentDescription = it.name,
            modifier = Modifier.size(32.dp),
            contentScale = ContentScale.FillBounds,
        )
    }
}