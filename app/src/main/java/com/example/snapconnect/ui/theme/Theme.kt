package com.example.snapconnect.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = SnapYellow,
    secondary = SnapBlue,
    tertiary = SnapTeal,
    background = DarkBackground,
    surface = DarkSurface,
    surfaceVariant = DarkSurfaceVariant,
    onPrimary = SnapBlack,
    onSecondary = WarmGray100,
    onTertiary = WarmGray100,
    onBackground = WarmGray100,
    onSurface = WarmGray100,
    onSurfaceVariant = WarmGray300,
    primaryContainer = SnapYellow.copy(alpha = 0.12f),
    secondaryContainer = SnapBlue.copy(alpha = 0.12f),
    tertiaryContainer = SnapTeal.copy(alpha = 0.12f),
    error = SnapRed,
    errorContainer = SnapRed.copy(alpha = 0.12f),
    onError = WarmGray100,
    onErrorContainer = SnapRed,
    outline = WarmGray600,
    outlineVariant = WarmGray700,
    scrim = SnapBlack.copy(alpha = 0.8f)
)

private val LightColorScheme = lightColorScheme(
    primary = SnapYellow,
    secondary = SnapBlue,
    tertiary = SnapTeal,
    background = WarmGray100,
    surface = WarmGray100,
    surfaceVariant = WarmGray200,
    onPrimary = SnapBlack,
    onSecondary = WarmGray100,
    onTertiary = WarmGray100,
    onBackground = SnapBlack,
    onSurface = SnapBlack,
    onSurfaceVariant = WarmGray700,
    primaryContainer = SnapYellow.copy(alpha = 0.12f),
    secondaryContainer = SnapBlue.copy(alpha = 0.12f),
    tertiaryContainer = SnapTeal.copy(alpha = 0.12f),
    error = SnapRed,
    errorContainer = SnapRed.copy(alpha = 0.12f),
    onError = WarmGray100,
    onErrorContainer = SnapRed,
    outline = WarmGray400,
    outlineVariant = WarmGray300,
    scrim = SnapBlack.copy(alpha = 0.5f)
)

@Composable
fun SnapConnectTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = if (darkTheme) DarkBackground.toArgb() else SnapYellow.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
} 