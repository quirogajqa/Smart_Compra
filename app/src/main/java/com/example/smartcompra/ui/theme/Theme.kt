package com.example.smartcompra.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF91ADC8),
    secondary = Color(0xFFAED6CF),
    tertiary = Color(0xFFFAFDD6),
    background = Color(0xFF647FBC),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color(0x6691ADC8),
    onSecondary = Color(0x66AED6CF),
    onTertiary = Color(0x66FAFDD6),
    onBackground = Color(0x66647FBC),
    onSurface = Color(0xFF1C1B1F)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFFAED6CF),
    secondary = Color(0xFF91ADC8),
    tertiary = Color(0xFF647FBC),
    background = Color(0xFFFAFDD6),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color(0x66AED6CF),
    onSecondary = Color(0x6691ADC8),
    onTertiary = Color(0x66647FBC),
    onBackground = Color(0x66FAFDD6),
    onSurface = Color(0xFF1C1B1F),

    )

@Composable
fun SmartCompraTheme(
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

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}