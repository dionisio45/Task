package org.dionisio.task.app.data.domain.repository.settings

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    suspend fun saveAppTheme(theme: Int)
    fun getAppTheme(): Flow<Int?>
    suspend fun clearAll()
    fun getSessionTime(): Flow<Int?>
    fun getShortBreakTime(): Flow<Int?>
    fun getLongBreakTime(): Flow<Int?>
    fun getHourFormat(): Flow<Int?>
    suspend fun saveSessionTime(sessionTime: Int)
    suspend fun saveLongBreakTime(longBreakTime: Int)
    suspend fun saveHourFormat(timeFormat: Int)
    suspend fun saveShortBreakTime(shortBreakTime: Int)
    fun getShortBreakColor(): Flow<Long?>
    suspend fun saveShortBreakColor(color: Long)
    fun longBreakColor(): Flow<Long?>
    suspend fun saveLongBreakColor(color: Long)
    fun focusColor(): Flow<Long?>
    suspend fun saveFocusColor(color: Long)
    suspend fun saveUsername(value: String)
    fun getUsername(): Flow<String?>
    fun remindersOn(): Flow<Int?>
    suspend fun toggleReminder(reminder: Int)
}
