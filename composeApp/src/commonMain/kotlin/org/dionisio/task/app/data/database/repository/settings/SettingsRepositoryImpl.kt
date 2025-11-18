package org.dionisio.task.app.data.database.repository.settings

import org.dionisio.task.app.data.database.local.setting.PreferenceManager
import org.dionisio.task.app.data.domain.repository.settings.SettingsRepository
import kotlinx.coroutines.flow.Flow

class SettingsRepositoryImpl(
    private val preferenceManager: PreferenceManager,
) : SettingsRepository {
    override suspend fun saveAppTheme(theme: Int) {
        preferenceManager.setInt(key = PreferenceManager.APP_THEME, value = theme)
    }

    override fun getAppTheme(): Flow<Int?> {
        return preferenceManager.getInt(key = PreferenceManager.APP_THEME)
    }

    override suspend fun clearAll() {
        return preferenceManager.clearPreferences()
    }

    override fun getSessionTime(): Flow<Int?> {
        return preferenceManager.getInt(key = PreferenceManager.FOCUS_TIME)
    }

    override fun getShortBreakTime(): Flow<Int?> {
        return preferenceManager.getInt(key = PreferenceManager.SHORT_BREAK_TIME)
    }

    override fun getLongBreakTime(): Flow<Int?> {
        return preferenceManager.getInt(key = PreferenceManager.LONG_BREAK_TIME)
    }

    override fun getHourFormat(): Flow<Int?> {
        return preferenceManager.getInt(key = PreferenceManager.HOUR_FORMAT)
    }

    override suspend fun saveSessionTime(sessionTime: Int) {
        preferenceManager.setInt(key = PreferenceManager.FOCUS_TIME, value = sessionTime)
    }

    override suspend fun saveLongBreakTime(longBreakTime: Int) {
        preferenceManager.setInt(key = PreferenceManager.LONG_BREAK_TIME, value = longBreakTime)
    }

    override suspend fun saveHourFormat(timeFormat: Int) {
        preferenceManager.setInt(key = PreferenceManager.HOUR_FORMAT, value = timeFormat)
    }

    override suspend fun saveShortBreakTime(shortBreakTime: Int) {
        preferenceManager.setInt(key = PreferenceManager.SHORT_BREAK_TIME, value = shortBreakTime)
    }

    override fun getShortBreakColor(): Flow<Long?> {
        return preferenceManager.getLong(key = PreferenceManager.SHORT_BREAK_COLOR)
    }

    override suspend fun saveShortBreakColor(color: Long) {
        preferenceManager.setLong(key = PreferenceManager.SHORT_BREAK_COLOR, value = color)
    }

    override fun longBreakColor(): Flow<Long?> {
        return preferenceManager.getLong(key = PreferenceManager.LONG_BREAK_COLOR)
    }

    override suspend fun saveLongBreakColor(color: Long) {
        preferenceManager.setLong(key = PreferenceManager.LONG_BREAK_COLOR, value = color)
    }

    override fun focusColor(): Flow<Long?> {
        return preferenceManager.getLong(key = PreferenceManager.FOCUS_COLOR)
    }

    override suspend fun saveFocusColor(color: Long) {
        preferenceManager.setLong(key = PreferenceManager.FOCUS_COLOR, value = color)
    }

    override suspend fun saveUsername(value: String) {
        preferenceManager.setString(key = PreferenceManager.USERNAME, value = value)
    }

    override fun getUsername(): Flow<String?> {
        return preferenceManager.getString(key = PreferenceManager.USERNAME)
    }

    override fun remindersOn(): Flow<Int?> {
        return preferenceManager.getInt(key = PreferenceManager.NOTIFICATION_OPTION)
    }

    override suspend fun toggleReminder(reminder: Int) {
        preferenceManager.setInt(key = PreferenceManager.NOTIFICATION_OPTION, value = reminder)
    }
}
