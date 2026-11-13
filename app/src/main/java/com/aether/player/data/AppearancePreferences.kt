package com.aether.player.data

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object AppearancePreferences {
    private const val FILE = "aether_appearance"
    private var observableTheme by mutableStateOf<String?>(null)
    private fun prefs(context: Context) = context.getSharedPreferences(FILE, Context.MODE_PRIVATE)

    fun theme(context: Context): String = observableTheme ?: (prefs(context).getString("theme", "AMOLED") ?: "AMOLED")
    fun setTheme(context: Context, value: String) {
        prefs(context).edit().putString("theme", value).apply()
        observableTheme = value
    }
    fun glass(context: Context): Boolean = prefs(context).getBoolean("glass", true)
    fun setGlass(context: Context, value: Boolean) { prefs(context).edit().putBoolean("glass", value).apply() }
    fun glassStrength(context: Context): Float = prefs(context).getFloat("glass_strength", 0.72f)
    fun setGlassStrength(context: Context, value: Float) { prefs(context).edit().putFloat("glass_strength", value.coerceIn(0f, 1f)).apply() }
    fun motion(context: Context): Boolean = prefs(context).getBoolean("motion", true)
    fun setMotion(context: Context, value: Boolean) { prefs(context).edit().putBoolean("motion", value).apply() }
    fun haptics(context: Context): Boolean = prefs(context).getBoolean("haptics", true)
    fun setHaptics(context: Context, value: Boolean) { prefs(context).edit().putBoolean("haptics", value).apply() }
}
