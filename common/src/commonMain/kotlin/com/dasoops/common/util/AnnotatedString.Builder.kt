package com.dasoops.common.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString

@Composable
fun AnnotatedString.Builder.append(
    text: String,
    style: TextStyle? = null,
) {
    append(buildAnnotatedString {
        append(text)
        style?.let {
            addStyle(
                style = it.toSpanStyle(),
                start = 0, end = length
            )
        }
    })
}