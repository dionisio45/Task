package org.dionisio.task.app.feature.settings

data class SettingsState(
    val appTheme: Int? = null,
    val sessionTime: Int? = null,
    val shortBreakTime: Int? = null,
    val longBreakTime: Int? = null,
    val timeFormat: Int? = null,
    val focusColor: Long? = null,
    val shortBreakColor: Long? = null,
    val longBreakColor: Long? = null,
    val remindersOn: Int? = null,
    val hourFormats: List<String> = listOf("12-hour", "24-hour"),
    val showColorDialog: Boolean = false,
    val selectedColorCardTitle: String = "",
    val optionsOpened: List<String> = emptyList()
)
