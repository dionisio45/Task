package org.dionisio.task.app.feature.settings

sealed interface SettingsAction {
    data class OnSetAppTheme(val value: Int) : SettingsAction
    data class OnSetSessionTime(val value: Int) : SettingsAction
    data class OnSetShortBreakTime(val value: Int) : SettingsAction
    data class OnSetLongBreakTime(val value: Int) : SettingsAction
    data class OnSetHourFormat(val value: Int) : SettingsAction
    data class OnSetFocusColor(val color: Long) : SettingsAction
    data class OnSetShortBreakColor(val color: Long) : SettingsAction
    data class OnSetLongBreakColor(val color: Long) : SettingsAction
    data class OnToggleReminder(val value: Int) : SettingsAction
    data class OnSetSelectedColorCardTitle(val title: String) : SettingsAction
    object OnToggleColorDialog : SettingsAction
    data class OnToggleOption(val option: String) : SettingsAction
}