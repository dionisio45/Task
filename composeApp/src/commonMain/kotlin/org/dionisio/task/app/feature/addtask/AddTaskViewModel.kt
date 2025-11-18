package org.dionisio.task.app.feature.addtask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.dionisio.task.app.data.utils.taskTypes
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import org.dionisio.task.app.data.domain.model.Task
import org.dionisio.task.app.data.domain.usecase.setting.SettingsUseCases
import org.dionisio.task.app.data.domain.usecase.task.TaskUseCases
import org.dionisio.task.app.utils.UiEvents
import org.dionisio.task.app.utils.calculateFromFocusSessions

class AddTaskViewModel(
    private val taskUseCases: TaskUseCases,
    private val settingsUseCases: SettingsUseCases
) : ViewModel() {
    private val _state = MutableStateFlow(AddTaskState())
    val state = _state.asStateFlow()

    private val _events = Channel<UiEvents>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    init {
        viewModelScope.launch {
            settingsUseCases.getHourFormat().collectLatest { format ->
                _state.update { it.copy(hourFormat = format?: 24) }
            }
        }
        // Tiempo de sesión
        viewModelScope.launch {
            settingsUseCases.getSessionTime().collectLatest { time ->
                _state.update { it.copy(sessionTime = time ?: 25) }
            }
        }

        // Descanso corto
        viewModelScope.launch {
            settingsUseCases.getShortBreakTime().collectLatest { time ->
                _state.update { it.copy(shortBreakTime = time ?: 5) }
            }
        }

        // Descanso largo
        viewModelScope.launch {
            settingsUseCases.getLongBreakTime().collectLatest { time ->
                _state.update { it.copy(longBreakTime = time ?: 15) }
            }
        }
    }

   fun onAction(action: AddTaskAction) {
        when (action) {
            is AddTaskAction.OnSetName -> _state.update { it.copy(name = action.name) }
            is AddTaskAction.OnSetDescription -> _state.update { it.copy(description = action.description) }
            is AddTaskAction.OnSetType -> _state.update { it.copy(type = action.type) }
            is AddTaskAction.OnSetDate -> _state.update { it.copy(taskDate = action.date) }
            is AddTaskAction.OnSetStartTime -> _state.update { it.copy(startTime = action.time)}
            is AddTaskAction.OnSetFocusSessions -> _state.update { it.copy(focusSessions = action.sessions) }
            is AddTaskAction.OnShowStartTimeInputDialog -> _state.update { it.copy(showStartTimeInputDialog = action.show) }
            is AddTaskAction.OnShowEndTimeDialog -> _state.update { it.copy(showEndTimeDialog = action.show) }
            is AddTaskAction.OnTaskShowDatePickerDialog -> _state.update { it.copy(showTaskDatePickerDialog = action.show) }

            is AddTaskAction.OnIncrementSessions -> updateFocus(1)
            is AddTaskAction.OnDecrementSessions -> updateFocus(-1)

            is AddTaskAction.OnAddOrUpdateTask -> saveTask(action.task)
            is AddTaskAction.OnLoadTask -> loadTask(action.taskId)
            is AddTaskAction.OnReset -> reset()
            else -> {}
        }
    }

    fun showSnackbar(message: String) {
        viewModelScope.launch {
            _events.send(UiEvents.ShowSnackbar(message))
        }
    }

    private fun updateFocus(delta: Int) {
        val newValue = (_state.value.focusSessions + delta).coerceAtLeast(1)
        val s = _state.value

        val newEnd = calculateFromFocusSessions(
            focusSessions = newValue,
            sessionTime = state.value.sessionTime,
            shortBreakTime = state.value.shortBreakTime,
            longBreakTime = state.value.longBreakTime,
            currentLocalDateTime = LocalDateTime(
                s.taskDate.year, s.taskDate.month, s.taskDate.day,
                s.startTime.hour, s.startTime.minute
            )
        )
        _state.update { it.copy(focusSessions = newValue, endTime = newEnd) }
    }

    private fun loadTask(taskId: Int?) {
        viewModelScope.launch {
            if (taskId == null) {
                reset()
                return@launch
            }

            _state.update { it.copy(isLoading = true) }

            val loaded = taskUseCases.getTask(taskId).firstOrNull()

            if (loaded != null) {
                _state.update {
                    it.copy(
                        id = loaded.id,
                        name = loaded.name,
                        description = loaded.description ?: "",
                        type = taskTypes.firstOrNull { t -> t.name == loaded.type } ?: taskTypes.last(),
                        startTime = loaded.start.time,
                        taskDate = loaded.date,
                        color = loaded.color,
                        current = loaded.current,
                        focusSessions = loaded.focusSessions,
                        currentCycle = loaded.currentCycle,
                        completed = loaded.completed,
                        consumedFocusTime = loaded.consumedFocusTime,
                        consumedShortBreakTime = loaded.consumedShortBreakTime,
                        consumedLongBreakTime = loaded.consumedLongBreakTime,
                        inProgressTask = loaded.inProgressTask,
                        active = loaded.active,
                        isLoading = false
                    )
                }
            } else {
                reset()
            }
        }
    }

    private fun saveTask(task: Task) {
        viewModelScope.launch {
            val s = _state.value
            val startDateTime = LocalDateTime(
                year = s.taskDate.year,
                month = s.taskDate.month,
                day = s.taskDate.day,
                hour = s.startTime.hour,
                minute = s.startTime.minute,
            )

            val newTask = Task(
                id = s.id,
                name = s.name,
                description = s.description.ifEmpty { null },
                type = s.type.name,
                start = startDateTime,
                date = startDateTime,
                color = s.color,
                current = s.current,
                focusSessions = s.focusSessions,
                currentCycle = s.currentCycle,
                completed = s.completed,
                consumedFocusTime = s.consumedFocusTime,
                consumedShortBreakTime = s.consumedShortBreakTime,
                consumedLongBreakTime = s.consumedLongBreakTime,
                inProgressTask = s.inProgressTask,
                active = s.active
            )

            try {
                taskUseCases.addTask(newTask)
                _events.send(UiEvents.ShowSnackbar("Task saved successfully"))
                _events.send(UiEvents.NavigateBack)
                reset()
            } catch (e: Exception) {
                _events.send(UiEvents.ShowSnackbar("Error saving task: ${e.message}"))
                _state.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun reset() {
        _state.value = AddTaskState()
    }
}