package com.dasoops.starcraft2ArtanisNotSick.common.content.setting

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import com.dasoops.starcraft2ArtanisNotSick.common.component.PaddingOutlinedTextField

@Composable
internal fun Title(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(bottom = 12.dp)
    )
}

@Composable
internal fun Description(modifier: Modifier = Modifier, title: String, subTitle: String? = null) {
    Column(modifier = modifier.width(250.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
        )
        subTitle?.let {
            Text(
                text = subTitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
            )
        }
    }
}

@Composable
internal fun Select(
    modifier: Modifier = Modifier,
    text: @Composable () -> Unit,
    dropdownMenuItemContent: @Composable ColumnScope.(MutableState<Boolean>) -> Unit,
) {
    val expandedState = remember { mutableStateOf(false) }
    var expanded by expandedState
    TextButton(
        modifier = modifier,
        onClick = { expanded = true },
        contentPadding = PaddingValues(0.dp),
        border = BorderStroke(0.5.dp, color = MaterialTheme.colorScheme.onBackground),
        shape = MaterialTheme.shapes.extraSmall,
    ) {
        text()
        DropdownMenu(
            modifier = Modifier,
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            },
            content = { dropdownMenuItemContent(expandedState) }
        )
    }
}

@Composable
internal fun <T> SelectOption(
    title: String,
    subTitle: String,
    selectText: String,
    items: Collection<T>,
    itemText: (T) -> String,
    onSelectItem: (T) -> Unit,
) {
    Row(
        modifier = Modifier.height(45.dp)
    ) {
        Description(
            modifier = Modifier,
            title = title,
            subTitle = subTitle,
        )
        Box(
            modifier = Modifier,
            contentAlignment = Alignment.CenterStart
        ) {
            Select(
                text = { Text(text = selectText, style = MaterialTheme.typography.bodySmall) },
                modifier = Modifier.size(height = 30.dp, width = 160.dp),
            ) { (_, setExpanded) ->
                items.forEach {
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = itemText(it),
                                style = MaterialTheme.typography.bodySmall
                            )
                        },
                        onClick = {
                            onSelectItem(it)
                            setExpanded(false)
                        }
                    )
                }
            }
        }
    }
}

@Composable
internal fun SwitchOption(
    title: String,
    subTitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.height(45.dp)
    ) {
        Description(
            modifier = Modifier,
            title = title,
            subTitle = subTitle
        )
        Box(
            modifier = Modifier,
        ) {
            Switch(
                modifier = Modifier.scale(0.7f).offset(x = (-12).dp),
                checked = checked,
                onCheckedChange = onCheckedChange,
            )
        }
    }
}

@Composable
internal fun InputOption(
    title: String,
    subTitle: String,
    value: String,
    onValueChange: (String) -> Unit,
) {
    Row(
        modifier = Modifier.height(45.dp)
    ) {
        Description(
            modifier = Modifier,
            title = title,
            subTitle = subTitle
        )
        PaddingOutlinedTextField(
            modifier = Modifier.size(width = 160.dp, height = 30.dp),
            textStyle = MaterialTheme.typography.bodySmall,
            contentPadding = PaddingValues(horizontal = 6.dp),
            value = value,
            singleLine = true,
            onValueChange = onValueChange,
        )
    }
}