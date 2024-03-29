package com.dasoops.starcraft2ArtanisNotSick.common.content.mission.missionChooser

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.dasoops.starcraft2ArtanisNotSick.common.resources.R
import com.dasoops.starcraft2ArtanisNotSick.common.resources.localization.str
import com.dasoops.starcraft2ArtanisNotSick.common.resources.mission.image
import com.dasoops.starcraft2ArtanisNotSick.common.resources.mission.missions

@Composable
internal fun MissionSelect(
    component: MissionChooserComponent,
) {
    MissionContainer(
        data = R.missions,
        columnCount = 3,
        modifier = Modifier.padding(top = 24.dp)
    ) {
        if (null == it) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(10.dp).weight(1f),
                content = {}
            )
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .clickable(onClick = { component.chooseMission(it) })
                    .padding(10.dp)
                    .weight(1f),
            ) {
                Image(
                    painter = it.image,
                    contentDescription = R.str.screen.mission.mission(it).name,
                    contentScale = ContentScale.FillBounds
                )
                Text(text = R.str.screen.mission.mission(it).name, maxLines = 1)
            }
        }
    }
}

@Composable
private inline fun <T> MissionContainer(
    data: Collection<T>,
    modifier: Modifier = Modifier,
    columnCount: Int = 3,
    crossinline content: @Composable RowScope.(T?) -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth()
    ) {
        data.chunked(columnCount).forEach { columnList ->
            item {
                Row {
                    (0 until columnCount).forEach {
                        content(columnList.getOrNull(it))
                    }
                }
            }
        }
    }
}