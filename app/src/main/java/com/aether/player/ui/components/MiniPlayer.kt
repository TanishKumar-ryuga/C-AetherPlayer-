package com.aether.player.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import com.aether.player.media.HapticsController
import com.aether.player.ui.PlayerViewModel

@Composable
fun MiniPlayer(vm: PlayerViewModel) {
    val s = vm.nowSong
    val c = vm.controller
    val context = LocalContext.current
    val view = LocalView.current
    GlassCard(modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp, vertical = 4.dp)) {
        Row(Modifier.fillMaxWidth().clickable { vm.showNow = true }.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Art(s, Modifier.size(40.dp))
            Column(Modifier.weight(1f).padding(horizontal = 10.dp)) {
                Text(s?.title ?: "", maxLines = 1, overflow = TextOverflow.Ellipsis)
                Text(s?.artist ?: "", style = MaterialTheme.typography.bodySmall, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
            TextButton(onClick = { HapticsController.tick(context, view); c?.seekToPreviousMediaItem() }) { Text("⏮") }
            FilledTonalButton(onClick = { HapticsController.tick(context, view); if (c != null) { if (c.isPlaying) c.pause() else c.play() } }) {
                Text(if (vm.isPlaying) "Pause" else "Play")
            }
            TextButton(onClick = { HapticsController.tick(context, view); c?.seekToNextMediaItem() }) { Text("⏭") }
        }
    }
}
