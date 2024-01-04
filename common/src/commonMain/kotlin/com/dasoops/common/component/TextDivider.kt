package com.dasoops.common.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TextDivider(
    text: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Divider(modifier = Modifier.height(1.dp).weight(1f))
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = 2.dp),
            style = MaterialTheme.typography.bodySmall,
        )
        Divider(modifier = Modifier.height(1.dp).weight(1f))
    }
}