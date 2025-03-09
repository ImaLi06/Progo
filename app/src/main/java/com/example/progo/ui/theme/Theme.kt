package com.example.progo.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    background = ThemeColors.Night.backGround,
    onBackground = ThemeColors.Night.mainText,
    primary = ThemeColors.Night.primary,
    secondary = ThemeColors.Night.secondary,
    onPrimary = ThemeColors.Night.textA,
    onSecondary = ThemeColors.Night.textB
)

private val LightColorScheme = lightColorScheme(
    background = ThemeColors.Day.backGround,
    onBackground = ThemeColors.Day.mainText,
    primary = ThemeColors.Day.primary,
    secondary = ThemeColors.Day.secondary,
    onPrimary = ThemeColors.Day.textA,
    onSecondary = ThemeColors.Day.textB

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun ProgoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}