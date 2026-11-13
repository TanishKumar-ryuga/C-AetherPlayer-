package com.aether.player.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.aether.player.data.AppearancePreferences

private val AetherAmoled = darkColorScheme(
    primary = Color(0xFFB8C7FF), onPrimary = Color(0xFF17264A),
    secondary = Color(0xFF9ED9D0), background = Color(0xFF000000),
    onBackground = Color(0xFFF4F4F7), surface = Color(0xFF07080B),
    onSurface = Color(0xFFF4F4F7), surfaceVariant = Color(0xFF17191F),
    onSurfaceVariant = Color(0xFFB8BBC5)
)

private val AetherMidnight = darkColorScheme(
    primary = Color(0xFF9DBBFF), secondary = Color(0xFF8BD8D0),
    background = Color(0xFF050A16), surface = Color(0xFF0B1425),
    surfaceVariant = Color(0xFF17243A)
)

private val AetherGraphite = darkColorScheme(
    primary = Color(0xFFD0D5E2), secondary = Color(0xFFA7B0C2),
    background = Color(0xFF090A0C), surface = Color(0xFF14161A),
    surfaceVariant = Color(0xFF22252B)
)

private val AetherLight = lightColorScheme(
    primary = Color(0xFF334E91), secondary = Color(0xFF286E68),
    background = Color(0xFFF5F6FA), surface = Color(0xFFFFFFFF)
)

@Composable
fun AetherTheme(content: @Composable () -> Unit) {
    val ctx = LocalContext.current
    val scheme = when (AppearancePreferences.theme(ctx)) {
        "Light" -> AetherLight
        "Midnight" -> AetherMidnight
        "Graphite" -> AetherGraphite
        else -> AetherAmoled
    }
    MaterialTheme(colorScheme = scheme, content = content)
}
