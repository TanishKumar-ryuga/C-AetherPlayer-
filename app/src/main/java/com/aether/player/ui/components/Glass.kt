package com.aether.player.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import com.aether.player.data.AppearancePreferences

/** Lightweight glass-like surface: layered translucency and edge highlights, with no mandatory GPU blur. */
@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    radius: Dp = 22.dp,
    strength: Float = 0.72f,
    content: @Composable BoxScope.() -> Unit
) {
    val context = LocalContext.current
    val shape = RoundedCornerShape(radius)
    val enabled = AppearancePreferences.glass(context)
    val intensity = if (enabled) (AppearancePreferences.glassStrength(context) * strength).coerceIn(0f, 1f) else 0f
    val alpha = if (enabled) (0.035f + intensity * 0.105f) else 0f
    Surface(
        modifier = modifier,
        shape = shape,
        color = Color.White.copy(alpha = alpha),
        border = if (enabled) BorderStroke(
            1.dp,
            Brush.linearGradient(
                listOf(Color.White.copy(alpha = 0.34f), Color.White.copy(alpha = 0.055f), Color.White.copy(alpha = 0.19f))
            )
        ) else null,
        tonalElevation = 0.dp,
        shadowElevation = if (enabled) 8.dp else 0.dp
    ) {
        Box(
            Modifier
                .clip(shape)
                .background(
                    Brush.linearGradient(
                        listOf(Color.White.copy(alpha = 0.075f * intensity), Color.Transparent, Color.White.copy(alpha = 0.025f * intensity))
                    )
                ),
            content = content
        )
    }
}
