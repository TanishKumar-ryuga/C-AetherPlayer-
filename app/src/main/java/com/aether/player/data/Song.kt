package com.aether.player.data

import android.net.Uri

data class Song(
    val id: Long,
    val title: String,
    val artist: String,
    val album: String,
    val uri: Uri,
    val path: String,
    val folder: String,
    val dateAdded: Long,
    val dateModified: Long = 0L,
    val sizeBytes: Long = 0L,
    val durationMs: Long = 0L,
    val mimeType: String = "",
) {
    val extension: String get() = path.substringAfterLast('.', "").lowercase()
    val formatLabel: String get() = when (extension) {
        "flac" -> "FLAC"
        "wav" -> "WAV"
        "m4a" -> "M4A"
        "aac" -> "AAC"
        "ogg" -> "OGG"
        "opus" -> "OPUS"
        "mp3" -> "MP3"
        "alac" -> "ALAC"
        else -> extension.uppercase().ifBlank { "UNKNOWN" }
    }
}
