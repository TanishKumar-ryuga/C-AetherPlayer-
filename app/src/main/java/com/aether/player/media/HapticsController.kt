package com.aether.player.media

import android.content.Context
import android.view.HapticFeedbackConstants
import android.view.View
import com.aether.player.data.AppearancePreferences

object HapticsController {
    fun tick(context: Context, view: View) {
        if (AppearancePreferences.haptics(context)) {
            view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
        }
    }
}
