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

// Paleta Elegante y Llamativa
val VerdeEsmeraldaProfundo = Color(0xFF00796B) // Primary
val TurquesaClaro = Color(0xFF4DB6AC)       // Secondary
val AmarilloAmbarVibrante = Color(0xFFFFC107) // Acento Llamativo
val GrisMuyClaro = Color(0xFFF5F5F5)        // Background

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF80CBC4),      // Verde Menta Claro
    onPrimary = Color.Black,
    secondary = Color(0xFF26A69A),      // Turquesa Oscuro
    onSecondary = Color.White,
    tertiary = Color(0xFFFFC107),     // Amarillo Ámbar (Acento)
    background = Color(0xFF121212),   // Negro Oscuro
    surface = Color(0xFF1D1D1D),      // Fondo de las tarjetas (cards)
    onSurface = Color.White
)

private val LightColorScheme = lightColorScheme(
    // Colores Principales del Esquema
    primary = VerdeEsmeraldaProfundo,   // Barra Superior, Barra Inferior, Botones principales
    onPrimary = Color.White,            // Texto e iconos sobre Primary

    // Colores Secundarios
    secondary = TurquesaClaro,          // Usar para elementos de menor importancia
    onSecondary = Color.Black,

    // Color Terciario (Acento Llamativo)
    tertiary = AmarilloAmbarVibrante,   // ¡USADO PARA RESALTAR DESCUENTOS Y PRECIO FINAL EN CARDS!
    onTertiary = Color.Black,

    // Colores de Fondo
    background = GrisMuyClaro,          // Fondo de la pantalla principal
    onBackground = Color.Black,

    // Colores de Superficie (Cards)
    surface = Color.White,              // Fondo de las Cards
    onSurface = Color.Black
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