package com.aether.player.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.aether.player.data.AppearancePreferences
import com.aether.player.ui.PlayerViewModel
import com.aether.player.ui.components.GlassCard

@Composable
fun AppearanceScreen(vm: PlayerViewModel) {
    val context = LocalContext.current
    val refresh = vm.appearanceTick
    val theme = remember(refresh) { AppearancePreferences.theme(context) }
    val glass = remember(refresh) { AppearancePreferences.glass(context) }
    val motion = remember(refresh) { AppearancePreferences.motion(context) }
    val haptics = remember(refresh) { AppearancePreferences.haptics(context) }
    var strength by remember(refresh) { mutableStateOf(AppearancePreferences.glassStrength(context)) }
    Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
        Text("Appearance Studio", style = MaterialTheme.typography.headlineMedium)
        Text("Make AetherPlayer feel like yours.", color = MaterialTheme.colorScheme.onSurfaceVariant)
        GlassCard(Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text("Colour theme", style = MaterialTheme.typography.titleMedium)
                listOf("AMOLED", "Midnight", "Graphite", "Light").forEach { option ->
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(option)
                        RadioButton(selected = theme == option, onClick = { vm.setTheme(option) })
                    }
                }
            }
        }
        GlassCard(Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Liquid Glass", style = MaterialTheme.typography.titleMedium)
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Enable glass surfaces")
                    Switch(checked = glass, onCheckedChange = vm::setGlass)
                }
                Text("Glass intensity · ${(strength * 100).toInt()}%", style = MaterialTheme.typography.bodySmall)
                Slider(value = strength, onValueChange = { strength = it }, onValueChangeFinished = { vm.setGlassStrength(strength) }, enabled = glass)
                Text("Translucent layers, fine highlights and soft depth. Blur effects use device-friendly fallbacks.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
        GlassCard(Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Enhanced motion")
                    Switch(checked = motion, onCheckedChange = vm::setMotion)
                }
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Haptic feedback")
                    Switch(checked = haptics, onCheckedChange = vm::setHaptics)
                }
            }
        }
        Text("More appearance controls will be added as the player screens are upgraded.", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}
