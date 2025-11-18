package org.dionisio.task.app.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.dionisio.task.app.data.domain.model.Task
import org.dionisio.task.app.data.domain.usecase.setting.SettingsUseCases
import org.dionisio.task.app.data.domain.usecase.task.TaskUseCases
import org.dionisio.task.app.utils.plusDays
import org.dionisio.task.app.utils.today
import kotlin.time.ExperimentalTime

class HomeViewModel(
    private val taskUseCases: TaskUseCases,
    private val settingsUseCases: SettingsUseCases
) : ViewModel() {
    private val _uiState = MutableStateFlow(HomeState())
    val uiState: StateFlow<HomeState> = _uiState

    init {
        observeSettings()
        observeTasks()
    }

    fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.OnOpenBottomSheet -> openBottomSheet(action.value)
            is HomeAction.OnSelectTask -> selectedTask(action.task)
            is HomeAction.OnDeleteSelectedTask -> deleteTask(action.task)
            is HomeAction.OnPushTaskToTomorrow -> pushToTomorrow(action.task)
            is HomeAction.OnPushTaskToToday -> pushToToday(action.task)
            is HomeAction.OnMarkTaskAsCompleted -> markAsCompleted(action.task)
            is HomeAction.OnToggleReminder -> toggleReminder(action.value)
        }
    }

    private fun observeSettings() {
        viewModelScope.launch {
            settingsUseCases.getHourFormat().collect { format ->
                _uiState.update { it.copy(hourFormat = format ?: 24) }
            }
        }
        viewModelScope.launch {
            settingsUseCases.getUsername().collect { username ->
                _uiState.update { it.copy(username = username) }
            }
        }
        viewModelScope.launch {
            settingsUseCases.getFocusColor().collect { color ->
                _uiState.update { it.copy(focusColor = color) }
            }
        }
        viewModelScope.launch {
            settingsUseCases.remindersOn().collect { reminderOnValue ->
                _uiState.update { it.copy(remindersOn = ReminderState.Success(reminderOnValue)) }
            }
        }
        viewModelScope.launch {
            settingsUseCases.getSessionTime().collect { session ->
                _uiState.update {
                    it.copy(sessionTime = session ?: 25)
                }
            }
            viewModelScope.launch {
                settingsUseCases.getShortBreakTime().collect { short ->
                    _uiState.update { it.copy(shortBreakTime = short ?: 5) }
                }
            }
            viewModelScope.launch {
                settingsUseCases.getLongBreakTime().collect { long ->
                    _uiState.update { it.copy(longBreakTime = long ?: 15) }
                }
            }
            viewModelScope.launch {
                settingsUseCases.getShortBreakColor().collect { shortColor ->
                    _uiState.update { it.copy(shortBreakColor = shortColor) }
                }
            }
            viewModelScope.launch {
                settingsUseCases.getLongBreakColor().collect { longColor ->
                    _uiState.update { it.copy(longBreakColor = longColor) }
                }
            }
        }
    }

    @OptIn(ExperimentalTime::class)
    private fun observeTasks() {
        viewModelScope.launch {
            taskUseCases.getTasks().collect { tasks ->
                val today = kotlin.time.Clock.System.now()
                    .toLocalDateTime(TimeZone.currentSystemDefault()).date

                val todayTasks = tasks
                    .sortedBy { it.start }
                    .filter { it.date.date == today }

                val overdueTasks = tasks
                    .sortedBy { it.start }
                    .filter { it.date.date < today && !it.completed }

                _uiState.update {
                    it.copy(
                        tasks = TasksState.Success(
                            tasks = todayTasks,
                            overdueTasks = overdueTasks
                        )
                    )
                }
            }
        }
    }

    fun openBottomSheet(value: Boolean) {
        _uiState.update { it.copy(isBottomSheetOpen = value) }
    }

    fun selectedTask(task: Task?) {
        _uiState.update { it.copy(selectedTask = task) }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskUseCases.deleteTask(task.id ?: 0)
        }
    }

    fun pushToTomorrow(task: Task) {
        viewModelScope.launch {
            taskUseCases.addTask(
                task.copy(
                    date = task.date.plusDays(1),
                    start = task.start.plusDays(1)
                )
            )
        }
    }

    fun pushToToday(task: Task) {
        viewModelScope.launch {
            taskUseCases.addTask(
                task.copy(
                    date = today(),
                    start = today()
                )
            )
        }
    }

    fun markAsCompleted(task: Task) {
        viewModelScope.launch {
            taskUseCases.updateTaskCompleted(task.id ?: 0, true)
            taskUseCases.updateTaskActive(task.id ?: 0, false)
            taskUseCases.updateTaskInProgress(task.id ?: 0, false)
        }
    }

    fun toggleReminder(value: Int) {
        viewModelScope.launch {
            settingsUseCases.toggleReminder(value)
        }
    }
}

sealed class TasksState {
    data object Loading : TasksState()
    data class Success(
        val tasks: List<Task>,
        val overdueTasks: List<Task>,
    ) : TasksState()
}

sealed class ReminderState {
    data object Loading : ReminderState()
    data class Success(val reminderOn: Int?) : ReminderState()
}