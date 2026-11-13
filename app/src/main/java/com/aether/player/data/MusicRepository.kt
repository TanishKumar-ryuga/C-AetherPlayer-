package com.aether.player.data

import android.content.ContentUris
import android.content.Context
import android.provider.MediaStore
import java.io.File

object MusicRepository {
    fun loadSongs(context: Context): List<Song> {
        val songs = mutableListOf<Song>()
        val collection = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.DATA,
            MediaStore.Audio.Media.DATE_ADDED,
            MediaStore.Audio.Media.DATE_MODIFIED,
            MediaStore.Audio.Media.SIZE,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.MIME_TYPE
        )
        context.contentResolver.query(
            collection,
            projection,
            "${MediaStore.Audio.Media.IS_MUSIC} != 0",
            null,
            "${MediaStore.Audio.Media.TITLE} COLLATE NOCASE ASC"
        )?.use { c ->
            val idCol = c.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)
            val titleCol = c.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)
            val artistCol = c.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)
            val albumCol = c.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)
            val pathCol = c.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
            val addedCol = c.getColumnIndexOrThrow(MediaStore.Audio.Media.DATE_ADDED)
            val modifiedCol = c.getColumnIndex(MediaStore.Audio.Media.DATE_MODIFIED)
            val sizeCol = c.getColumnIndex(MediaStore.Audio.Media.SIZE)
            val durationCol = c.getColumnIndex(MediaStore.Audio.Media.DURATION)
            val mimeCol = c.getColumnIndex(MediaStore.Audio.Media.MIME_TYPE)
            while (c.moveToNext()) {
                val id = c.getLong(idCol)
                val path = c.getString(pathCol) ?: ""
                songs += Song(
                    id = id,
                    title = c.getString(titleCol) ?: "Unknown",
                    artist = c.getString(artistCol) ?: "Unknown artist",
                    album = c.getString(albumCol) ?: "Unknown album",
                    uri = ContentUris.withAppendedId(collection, id),
                    path = path,
                    folder = File(path).parent ?: "",
                    dateAdded = c.getLong(addedCol),
                    dateModified = if (modifiedCol >= 0) c.getLong(modifiedCol) else 0L,
                    sizeBytes = if (sizeCol >= 0) c.getLong(sizeCol) else 0L,
                    durationMs = if (durationCol >= 0) c.getLong(durationCol) else 0L,
                    mimeType = if (mimeCol >= 0) c.getString(mimeCol).orEmpty() else ""
                )
            }
        }
        return songs
    }
}
