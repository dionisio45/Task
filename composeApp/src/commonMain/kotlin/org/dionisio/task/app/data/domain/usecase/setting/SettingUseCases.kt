package org.dionisio.task.app.data.domain.usecase.setting

data class SettingsUseCases(
    val saveAppTheme: SaveAppTheme,
    val getAppTheme: GetAppTheme,
    val saveUsername: SaveUsername,
    val getUsername: GetUsername,
    val saveSessionTime: SaveSessionTime,
    val getSessionTime: GetSessionTime,
    val saveShortBreakTime: SaveShortBreakTime,
    val getShortBreakTime: GetShortBreakTime,
    val saveLongBreakTime: SaveLongBreakTime,
    val getLongBreakTime: GetLongBreakTime,
    val saveHourFormat: SaveHourFormat,
    val getHourFormat: GetHourFormat,
    val saveFocusColor: SaveFocusColor,
    val getFocusColor: GetFocusColor,
    val saveShortBreakColor: SaveShortBreakColor,
    val getShortBreakColor: GetShortBreakColor,
    val saveLongBreakColor: SaveLongBreakColor,
    val getLongBreakColor: GetLongBreakColor,
    val toggleReminder: ToggleReminder,
    val remindersOn: RemindersOn,
    val clearAll: ClearAllSettings
)

