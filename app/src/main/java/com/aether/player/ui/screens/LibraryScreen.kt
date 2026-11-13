package com.aether.player.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.aether.player.data.SongSort
import com.aether.player.ui.Detail
import com.aether.player.ui.Group
import com.aether.player.ui.PlayerViewModel
import com.aether.player.ui.components.GroupList
import com.aether.player.ui.components.VmSongList

@Composable
fun LibraryScreen(vm: PlayerViewModel) {
    var sortMenu by remember { mutableStateOf(false) }
    var secondaryMenu by remember { mutableStateOf(false) }
    var formatMenu by remember { mutableStateOf(false) }
    val formats = listOf("All formats") + vm.songs.map { it.formatLabel }.distinct().sorted()
    Column(Modifier.fillMaxSize()) {
        TabRow(selectedTabIndex = vm.libTab) {
            listOf("Songs", "Albums", "Artists").forEachIndexed { i, n ->
                Tab(selected = vm.libTab == i, onClick = { vm.libTab = i }, text = { Text(n) })
            }
        }
        if (vm.libTab == 0) {
            OutlinedTextField(
                value = vm.searchQuery,
                onValueChange = { vm.searchQuery = it },
                modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 8.dp),
                singleLine = true,
                label = { Text("Search your library") },
                leadingIcon = { Text("⌕") },
                trailingIcon = { if (vm.searchQuery.isNotEmpty()) TextButton(onClick = { vm.searchQuery = "" }) { Text("Clear") } }
            )
            Row(
                Modifier.fillMaxWidth().padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box {
                    OutlinedButton(onClick = { sortMenu = true }) { Text("Sort · ${vm.sortOption.label}", maxLines = 1, overflow = TextOverflow.Ellipsis) }
                    DropdownMenu(expanded = sortMenu, onDismissRequest = { sortMenu = false }) {
                        SongSort.values().forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option.label + if (vm.sortOption == option) " ✓" else "") },
                                onClick = { vm.updateSortOption(option); sortMenu = false }
                            )
                        }
                    }
                }
                Box {
                    OutlinedButton(onClick = { secondaryMenu = true }) { Text("Then by") }
                    DropdownMenu(expanded = secondaryMenu, onDismissRequest = { secondaryMenu = false }) {
                        DropdownMenuItem(text = { Text("None") }, onClick = { vm.secondarySortOption = null; secondaryMenu = false })
                        SongSort.values().filter { it != vm.sortOption }.forEach { option ->
                            DropdownMenuItem(text = { Text(option.label) }, onClick = { vm.secondarySortOption = option; secondaryMenu = false })
                        }
                    }
                }
                Box {
                    OutlinedButton(onClick = { formatMenu = true }) { Text("Format · ${vm.formatFilter}") }
                    DropdownMenu(expanded = formatMenu, onDismissRequest = { formatMenu = false }) {
                        formats.forEach { format ->
                            DropdownMenuItem(text = { Text(format) }, onClick = { vm.formatFilter = format; formatMenu = false })
                        }
                    }
                }
            }
            if (vm.secondarySortOption != null) {
                Text("Then by: ${vm.secondarySortOption?.label}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp))
            }
            Text("${vm.visibleSongs.size} tracks", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp))
            VmSongList(vm, vm.visibleSongs, Modifier.weight(1f))
        } else if (vm.libTab == 1) {
            GroupList(
                vm.songs.groupBy { it.album }.map { (k, v) -> Group(k, v.first().artist, v) }.sortedBy { it.title.lowercase() }
            ) { vm.detail = Detail(it.title, it.songs) }
        } else {
            GroupList(
                vm.songs.groupBy { it.artist }.map { (k, v) -> Group(k, "", v) }.sortedBy { it.title.lowercase() }
            ) { vm.detail = Detail(it.title, it.songs) }
        }
    }
}

@Composable
fun FoldersScreen(vm: PlayerViewModel) {
    GroupList(
        vm.songs.groupBy { it.folder }.map { (k, v) -> Group(k.substringAfterLast('/'), k, v) }.sortedBy { it.title.lowercase() }
    ) { vm.detail = Detail(it.title, it.songs) }
}

@Composable
fun DetailScreen(vm: PlayerViewModel, d: Detail, onExport: (String) -> Unit) {
    Column {
        Row(Modifier.padding(horizontal = 8.dp), verticalAlignment = Alignment.CenterVertically) {
            TextButton(onClick = { vm.detail = null }) { Text("←") }
            Text(d.title, Modifier.weight(1f), maxLines = 1, overflow = TextOverflow.Ellipsis, style = MaterialTheme.typography.titleMedium)
        }
        Row(Modifier.padding(horizontal = 12.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { vm.playList(d.songs, 0, false) }) { Text("Play") }
            OutlinedButton(onClick = { if (d.songs.isNotEmpty()) vm.playList(d.songs, d.songs.indices.random(), true) }) { Text("Shuffle") }
            val pl = d.playlist
            if (pl != null) {
                OutlinedButton(onClick = { onExport(pl) }) { Text("Export") }
                OutlinedButton(onClick = { vm.deletePlaylist(pl); vm.detail = null }) { Text("Delete") }
            }
        }
        VmSongList(vm, d.songs, Modifier.weight(1f))
    }
}
