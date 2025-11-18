package org.dionisio.task.app.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.dionisio.task.app.data.domain.usecase.setting.SettingsUseCases

class SettingsViewModel(
    private val settingsUseCases: SettingsUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(SettingsState())
    val state: StateFlow<SettingsState> = _state.asStateFlow()

    init {
        observeSettings()
    }

    private fun observeSettings() {
        viewModelScope.launch {
            val partialFlow = combine(
                settingsUseCases.getAppTheme(),
                settingsUseCases.getSessionTime(),
                settingsUseCases.getShortBreakTime(),
                settingsUseCases.getLongBreakTime(),
                settingsUseCases.getHourFormat()
            ) { appTheme, session, shortBreak, longBreak, hourFormat ->
                PartialSettings(
                    appTheme = appTheme,
                    sessionTime = session,
                    shortBreakTime = shortBreak,
                    longBreakTime = longBreak,
                    hourFormat = hourFormat
                )
            }
            combine(
                partialFlow,
                settingsUseCases.getFocusColor(),
                settingsUseCases.getShortBreakColor(),
                settingsUseCases.getLongBreakColor(),
                settingsUseCases.remindersOn()
            ) { partial, focusColor, shortColor, longColor, reminders ->
                SettingsState(
                    appTheme = partial.appTheme,
                    sessionTime = partial.sessionTime,
                    shortBreakTime = partial.shortBreakTime,
                    longBreakTime = partial.longBreakTime,
                    timeFormat = partial.hourFormat,
                    focusColor = focusColor,
                    shortBreakColor = shortColor,
                    longBreakColor = longColor,
                    remindersOn = reminders
                )
            }.collect { newState ->
                _state.value = newState
            }
        }
    }

    private data class PartialSettings(
        val appTheme: Int?,
        val sessionTime: Int?,
        val shortBreakTime: Int?,
        val longBreakTime: Int?,
        val hourFormat: Int?
    )

    fun onAction(action: SettingsAction) {
        when (action) {
            is SettingsAction.OnSetAppTheme -> update { settingsUseCases.saveAppTheme(action.value) }
            is SettingsAction.OnSetSessionTime -> update { settingsUseCases.saveSessionTime(action.value) }
            is SettingsAction.OnSetShortBreakTime -> update { settingsUseCases.saveShortBreakTime(action.value)}
            is SettingsAction.OnSetLongBreakTime -> update { settingsUseCases.saveLongBreakTime(action.value) }
            is SettingsAction.OnSetHourFormat -> update { settingsUseCases.saveHourFormat(action.value) }
            is SettingsAction.OnSetFocusColor -> update { settingsUseCases.saveFocusColor(action.color) }
            is SettingsAction.OnSetShortBreakColor -> update { settingsUseCases.saveShortBreakColor(action.color) }
            is SettingsAction.OnSetLongBreakColor -> update { settingsUseCases.saveLongBreakColor( action.color) }
            is SettingsAction.OnToggleReminder -> update { settingsUseCases.toggleReminder(action.value) }
            is SettingsAction.OnSetSelectedColorCardTitle -> {
                _state.update { it.copy(selectedColorCardTitle = action.title) }
            }

            is SettingsAction.OnToggleOption -> {
                val options = _state.value.optionsOpened.toMutableList()
                if (options.contains(action.option)) {
                    options.remove(action.option)
                } else {
                    options.add(action.option)
                }
                _state.update { it.copy(optionsOpened = options) }
            }

            SettingsAction.OnToggleColorDialog -> {
                _state.update { it.copy(showColorDialog = !it.showColorDialog) }
            }
        }
    }

    private fun update(block: suspend () -> Unit) {
        viewModelScope.launch { block() }
    }
}