package edu.nd.pmcburne.hello.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = UVAOrange,
    secondary = UVANavyLight,
    tertiary = UVAOrange,
    background = UVANavy,
    surface = UVANavyLight,
    onPrimary = White,
    onSecondary = White,
    onBackground = White,
    onSurface = White
)

private val LightColorScheme = lightColorScheme(
    primary = UVANavy,
    secondary = UVAOrange,
    tertiary = UVANavyLight,
    background = LightGray,
    surface = White,
    onPrimary = White,
    onSecondary = White,
    onBackground = UVANavy,
    onSurface = UVANavy
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}