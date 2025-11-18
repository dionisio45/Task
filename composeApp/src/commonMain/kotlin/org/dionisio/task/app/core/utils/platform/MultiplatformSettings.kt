package org.dionisio.task.app.core.utils.platform

import com.russhwolf.settings.Settings

expect class MultiplatformSettings {
    fun createSettings(): Settings
}