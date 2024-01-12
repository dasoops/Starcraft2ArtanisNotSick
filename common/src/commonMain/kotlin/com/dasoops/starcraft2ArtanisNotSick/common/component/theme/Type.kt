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

package com.dasoops.starcraft2ArtanisNotSick.common.component.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import com.dasoops.starcraft2ArtanisNotSick.common.resources.R
import com.dasoops.starcraft2ArtanisNotSick.common.resources.font

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

private val fontFamilyConsolas = FontFamily(
    listOf(
        Font(
            resource = R.font.consolas
        ),
        Font(
            resource = R.font.consolasBold,
            weight = FontWeight.Bold
        )
    )
)


fun typography(
    color: Color,
    fontFamily: FontFamily = fontFamilyConsolas
) = Typography().run {
    Typography(
        displayLarge = this.displayLarge.copy(color = color, fontFamily = fontFamily),
        displayMedium = this.displayMedium.copy(color = color, fontFamily = fontFamily),
        displaySmall = this.displaySmall.copy(color = color, fontFamily = fontFamily),

        headlineLarge = this.headlineLarge.copy(color = color, fontFamily = fontFamily),
        headlineMedium = this.headlineMedium.copy(color = color, fontFamily = fontFamily),
        headlineSmall = this.headlineSmall.copy(color = color, fontFamily = fontFamily),

        titleLarge = this.titleLarge.copy(
            color = color,
            fontFamily = fontFamily,
            fontWeight = FontWeight.Bold
        ),
        titleMedium = this.titleMedium.copy(color = color, fontFamily = fontFamily),
        titleSmall = this.titleSmall.copy(color = color, fontFamily = fontFamily),

        bodyLarge = this.bodyLarge.copy(color = color, fontFamily = fontFamily),
        bodyMedium = this.bodyMedium.copy(color = color, fontFamily = fontFamily),
        bodySmall = this.bodySmall.copy(color = color, fontFamily = fontFamily),

        labelLarge = this.labelLarge.copy(color = color, fontFamily = fontFamily),
        labelMedium = this.labelMedium.copy(color = color, fontFamily = fontFamily),
        labelSmall = this.labelSmall.copy(color = color, fontFamily = fontFamily),
    )
}
