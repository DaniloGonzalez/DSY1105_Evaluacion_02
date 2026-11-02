package com.example.ev2_master_martini.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val ColorRedWine = Color(0xFF9B2230)
private val ColorRedWineLight = Color(0xFFE8A0AC)

private val DarkColorScheme = darkColorScheme(
    primary = MartiniWineLight,
    onPrimary = MartiniBurgundyDark,
    primaryContainer = MartiniBurgundy,
    onPrimaryContainer = MartiniCream,
    secondary = MartiniGoldLight,
    onSecondary = MartiniBurgundyDark,
    secondaryContainer = MartiniGoldDark,
    onSecondaryContainer = MartiniCream,
    tertiary = MartiniWineLight,
    background = MartiniBurgundyDark,
    onBackground = MartiniCream,
    surface = MartiniBurgundyDark,
    onSurface = MartiniCream,
    surfaceVariant = MartiniBurgundy,
    onSurfaceVariant = MartiniGoldLight,
    error = ColorRedWineLight
)

private val LightColorScheme = lightColorScheme(
    primary = MartiniWine,
    onPrimary = MartiniCream,
    primaryContainer = MartiniCreamLight,
    onPrimaryContainer = MartiniBurgundyDark,
    secondary = MartiniGoldDark,
    onSecondary = MartiniCream,
    secondaryContainer = MartiniGoldLight,
    onSecondaryContainer = MartiniBurgundyDark,
    tertiary = MartiniWine,
    background = MartiniCream,
    onBackground = MartiniBrown,
    surface = MartiniCream,
    onSurface = MartiniBrown,
    surfaceVariant = MartiniCreamLight,
    onSurfaceVariant = MartiniBrownSoft,
    error = ColorRedWine
)

@Composable
fun Ev2_Master_MartiniTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    fontScale: Float = 1f,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = scaledTypography(fontScale),
        content = content
    )
}

private fun scaledTypography(fontScale: Float): androidx.compose.material3.Typography {
    val base = Typography
    if (fontScale <= 1.0f) return base
    return Typography(
        displayLarge = base.displayLarge.copy(fontSize = base.displayLarge.fontSize * fontScale),
        displayMedium = base.displayMedium.copy(fontSize = base.displayMedium.fontSize * fontScale),
        displaySmall = base.displaySmall.copy(fontSize = base.displaySmall.fontSize * fontScale),
        headlineLarge = base.headlineLarge.copy(fontSize = base.headlineLarge.fontSize * fontScale),
        headlineMedium = base.headlineMedium.copy(fontSize = base.headlineMedium.fontSize * fontScale),
        headlineSmall = base.headlineSmall.copy(fontSize = base.headlineSmall.fontSize * fontScale),
        titleLarge = base.titleLarge.copy(fontSize = base.titleLarge.fontSize * fontScale),
        titleMedium = base.titleMedium.copy(fontSize = base.titleMedium.fontSize * fontScale),
        titleSmall = base.titleSmall.copy(fontSize = base.titleSmall.fontSize * fontScale),
        bodyLarge = base.bodyLarge.copy(fontSize = base.bodyLarge.fontSize * fontScale),
        bodyMedium = base.bodyMedium.copy(fontSize = base.bodyMedium.fontSize * fontScale),
        bodySmall = base.bodySmall.copy(fontSize = base.bodySmall.fontSize * fontScale),
        labelLarge = base.labelLarge.copy(fontSize = base.labelLarge.fontSize * fontScale),
        labelMedium = base.labelMedium.copy(fontSize = base.labelMedium.fontSize * fontScale),
        labelSmall = base.labelSmall.copy(fontSize = base.labelSmall.fontSize * fontScale)
    )
}