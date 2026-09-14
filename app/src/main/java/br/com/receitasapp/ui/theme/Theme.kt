package br.com.receitasapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * REQUISITO 8 - Material Design 3 aplicado tambem na tela feita em Compose,
 * usando as mesmas cores do tema XML para manter consistencia visual.
 */
private val CoresClaras = lightColorScheme(
    primary = Color(0xFFB4500A),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFFFDBC7),
    onPrimaryContainer = Color(0xFF221A15),
    secondary = Color(0xFF3E6B3C),
    onSecondary = Color(0xFFFFFFFF),
    background = Color(0xFFFFF8F5),
    onBackground = Color(0xFF221A15),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF221A15),
    surfaceVariant = Color(0xFFF5E6DC),
    onSurfaceVariant = Color(0xFF6F5B52)
)

@Composable
fun ReceitasTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = CoresClaras,
        content = content
    )
}
