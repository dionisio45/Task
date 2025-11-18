package org.dionisio.task.app.core.utils.platform

import android.content.Context
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings

actual class MultiplatformSettings(private val context: Context) {
    actual fun createSettings(): Settings {
        val delegate = context.getSharedPreferences("task_preferences", Context.MODE_PRIVATE)
        return SharedPreferencesSettings(delegate)
    }
}