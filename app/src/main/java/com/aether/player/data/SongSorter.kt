package com.aether.player.data

enum class SongSort(val label: String) {
    TITLE_ASC("Title · A–Z"), TITLE_DESC("Title · Z–A"),
    ARTIST_ASC("Artist · A–Z"), ARTIST_DESC("Artist · Z–A"),
    ALBUM_ASC("Album · A–Z"), ALBUM_DESC("Album · Z–A"),
    ADDED_NEWEST("Recently added"), ADDED_OLDEST("Oldest added"),
    MODIFIED_NEWEST("Recently modified"), MODIFIED_OLDEST("Oldest modified"),
    PLAYED_NEWEST("Recently played"), PLAYED_OLDEST("Least recently played"),
    MOST_PLAYED("Most played"), LEAST_PLAYED("Least played"),
    LONGEST("Longest duration"), SHORTEST("Shortest duration"),
    LARGEST("Largest file"), SMALLEST("Smallest file"),
    FORMAT_ASC("Format · A–Z"), FORMAT_DESC("Format · Z–A"),
    FOLDER_ASC("Folder · A–Z"), FOLDER_DESC("Folder · Z–A")
}

object SongSorter {
    fun sort(
        songs: List<Song>, option: SongSort,
        playCounts: Map<Long, Int> = emptyMap(),
        lastPlayed: Map<Long, Long> = emptyMap()
    ): List<Song> = when (option) {
        SongSort.TITLE_ASC -> songs.sortedBy { it.title.lowercase() }
        SongSort.TITLE_DESC -> songs.sortedByDescending { it.title.lowercase() }
        SongSort.ARTIST_ASC -> songs.sortedBy { it.artist.lowercase() }
        SongSort.ARTIST_DESC -> songs.sortedByDescending { it.artist.lowercase() }
        SongSort.ALBUM_ASC -> songs.sortedBy { it.album.lowercase() }
        SongSort.ALBUM_DESC -> songs.sortedByDescending { it.album.lowercase() }
        SongSort.ADDED_NEWEST -> songs.sortedByDescending { it.dateAdded }
        SongSort.ADDED_OLDEST -> songs.sortedBy { it.dateAdded }
        SongSort.MODIFIED_NEWEST -> songs.sortedByDescending { it.dateModified }
        SongSort.MODIFIED_OLDEST -> songs.sortedBy { it.dateModified }
        SongSort.PLAYED_NEWEST -> songs.sortedByDescending { lastPlayed[it.id] ?: 0L }
        SongSort.PLAYED_OLDEST -> songs.sortedBy { lastPlayed[it.id] ?: 0L }
        SongSort.MOST_PLAYED -> songs.sortedByDescending { playCounts[it.id] ?: 0 }
        SongSort.LEAST_PLAYED -> songs.sortedBy { playCounts[it.id] ?: 0 }
        SongSort.LONGEST -> songs.sortedByDescending { it.durationMs }
        SongSort.SHORTEST -> songs.sortedBy { it.durationMs }
        SongSort.LARGEST -> songs.sortedByDescending { it.sizeBytes }
        SongSort.SMALLEST -> songs.sortedBy { it.sizeBytes }
        SongSort.FORMAT_ASC -> songs.sortedBy { it.formatLabel }
        SongSort.FORMAT_DESC -> songs.sortedByDescending { it.formatLabel }
        SongSort.FOLDER_ASC -> songs.sortedBy { it.folder.lowercase() }
        SongSort.FOLDER_DESC -> songs.sortedByDescending { it.folder.lowercase() }
    }
}
