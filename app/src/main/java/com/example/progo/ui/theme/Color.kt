package com.example.progo.ui.theme

import androidx.compose.ui.graphics.Color

sealed class ThemeColors(
    val backGround: Color,
    val primary: Color,
    val secondary: Color,
    val textA: Color,
    val textB: Color
) {
    object Night: ThemeColors(
        backGround = Color(0xFF151515),
        primary = Color.Green,
        secondary = Color(0xFF232323),
        textA = Color(0xFF2C2C2C),
        textB = Color(0xFFFFFFFF)
    )

    object Day: ThemeColors(
        backGround = Color(0xFFFFFFFF),
        primary = Color.Green,
        secondary = Color(0xFFF3F3F3),
        textA = Color(0xFFFFFFFF),
        textB = Color(0xFF9D9D9D)
    )
}