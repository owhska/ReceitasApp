package br.com.receitasapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * Material Design 3 aplicado tambem na tela feita em Compose,
 * usando as mesmas cores do tema XML para manter consistencia visual.
 */
private val CoresClaras = lightColorScheme(
    primary = Color(0xFF1976D2),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFE3F2FD),
    onPrimaryContainer = Color(0xFF212121),
    secondary = Color(0xFF388E3C),
    onSecondary = Color(0xFFFFFFFF),
    background = Color(0xFFF5F5F5),
    onBackground = Color(0xFF212121),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF212121),
    surfaceVariant = Color(0xFFE0E0E0),
    onSurfaceVariant = Color(0xFF666666)
)

@Composable
fun ReceitasTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = CoresClaras,
        content = content
    )
}
