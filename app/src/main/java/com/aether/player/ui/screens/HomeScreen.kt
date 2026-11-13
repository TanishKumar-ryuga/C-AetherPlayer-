package com.aether.player.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.aether.player.data.PlayStatsStore
import com.aether.player.ui.PlayerViewModel
import com.aether.player.ui.components.GlassCard
import com.aether.player.ui.components.HomeRow

@Composable
fun HomeScreen(vm: PlayerViewModel) {
    val ctx = LocalContext.current
    val songs = vm.songs
    val counts = remember(vm.statsTick, songs) { PlayStatsStore.counts(ctx) }
    val last = remember(vm.statsTick, songs) { PlayStatsStore.last(ctx) }
    val recentAdded = songs.sortedByDescending { it.dateAdded }.take(15)
    val recentPlayed = last.entries.sortedByDescending { it.value }.mapNotNull { vm.byId[it.key] }.take(15)
    val most = counts.entries.sortedByDescending { it.value }.mapNotNull { vm.byId[it.key] }.take(15)

    LazyColumn {
        item {
            GlassCard(Modifier.fillMaxWidth().padding(12.dp)) {          // ← fixed
                Column(Modifier.padding(18.dp)) {
                    Text(
                        "YOUR SOUND, YOUR SPACE",
                        style = androidx.compose.material3.MaterialTheme.typography.labelSmall,
                        color = androidx.compose.material3.MaterialTheme.colorScheme.primary
                    )
                    Text("AetherPlayer", style = androidx.compose.material3.MaterialTheme.typography.headlineMedium)
                    Text("${songs.size} tracks in your library", style = androidx.compose.material3.MaterialTheme.typography.bodyMedium)
                    Button(
                        onClick = { if (songs.isNotEmpty()) vm.playList(songs, songs.indices.random(), true) },
                        modifier = Modifier.padding(top = 12.dp)
                    ) { Text("Shuffle your library") }
                }
            }
        }
        // ... rest of the file stays the same
        item { HomeRow("Recently added", recentAdded) { vm.playList(recentAdded, it, false) } }
        item { HomeRow("Recently played", recentPlayed) { vm.playList(recentPlayed, it, false) } }
        item { HomeRow("Most played", most) { vm.playList(most, it, false) } }
        item { HomeRow("Never played", songs.filter { (counts[it.id] ?: 0) == 0 }.take(30)) { vm.playList(songs.filter { (counts[it.id] ?: 0) == 0 }, it, false) } }
        item { HomeRow("FLAC collection", songs.filter { it.formatLabel == "FLAC" }.take(30)) { vm.playList(songs.filter { it.formatLabel == "FLAC" }, it, false) } }
        item { HomeRow("Longest tracks", songs.sortedByDescending { it.durationMs }.take(15)) { vm.playList(songs.sortedByDescending { it.durationMs }.take(15), it, false) } }
    }
}
