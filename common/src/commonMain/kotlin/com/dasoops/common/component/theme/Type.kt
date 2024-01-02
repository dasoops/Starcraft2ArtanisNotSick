/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dasoops.common.component.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import com.dasoops.common.resources.R
import com.dasoops.common.resources.font

private val fontFamilyKulim = FontFamily(
    listOf(
        Font(
            resource = R.font.kulimParkLight
        ),
        Font(
            resource = R.font.kulimParkLight,
            weight = FontWeight.Light
        )
    )
)

private val fontFamilyLato = FontFamily(
    listOf(
        Font(
            resource = R.font.latoRegular
        ),
        Font(
            resource = R.font.latoBold,
            weight = FontWeight.Bold
        )
    )
)

fun typography(color: Color) = Typography().run {
    Typography(
        displayLarge = this.displayLarge.copy(color = color),
        displayMedium = this.displayMedium.copy(color = color),
        displaySmall = this.displaySmall.copy(color = color),

        headlineLarge = this.headlineLarge.copy(color = color),
        headlineMedium = this.headlineMedium.copy(color = color),
        headlineSmall = this.headlineSmall.copy(color = color),

        titleLarge = this.titleLarge.copy(color = color, fontWeight = FontWeight.Bold),
        titleMedium = this.titleMedium.copy(color = color),
        titleSmall = this.titleSmall.copy(color = color),

        bodyLarge = this.bodyLarge.copy(color = color),
        bodyMedium = this.bodyMedium.copy(color = color),
        bodySmall = this.bodySmall.copy(color = color),

        labelLarge = this.labelLarge.copy(color = color),
        labelMedium = this.labelMedium.copy(color = color),
        labelSmall = this.labelSmall.copy(color = color),
    )
}
