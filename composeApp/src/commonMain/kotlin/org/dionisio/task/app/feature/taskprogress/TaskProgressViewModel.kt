package org.dionisio.task.app.feature.taskprogress

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.dionisio.task.app.data.domain.model.Task
import org.dionisio.task.app.data.domain.usecase.setting.SettingsUseCases
import org.dionisio.task.app.data.domain.usecase.task.TaskUseCases
import org.dionisio.task.app.utils.formattedNumber
import org.dionisio.task.app.utils.toMillis

class TaskProgressViewModel(
    private val settingsUseCases: SettingsUseCases,
    private val taskUseCases: TaskUseCases,
    //private val notificationManager: NotificationsManager,
) : ViewModel() {

    private val _state = MutableStateFlow(TaskProgressState())
    val state = _state.asStateFlow()

    init {
        observeSettings()
    }

    fun onAction(action: TaskProgressAction) {
        when (action) {
            is TaskProgressAction.LoadTask -> loadTask(action.taskId)
            is TaskProgressAction.TimerButtonClicked -> handleTimerButton(action.timerState)
            TaskProgressAction.Start -> startExecution()
            TaskProgressAction.MoveToNextSession -> moveToNext()
            TaskProgressAction.ResetSession -> resetCurrentSession()
        }
    }

    // --------------------------
    // SETTINGS LISTENERS
    // --------------------------
    private fun observeSettings() {
        viewModelScope.launch {
            settingsUseCases.getSessionTime().collect { minutes ->
                val millis = minutes?.toMillis() ?: 25.toMillis()
                _state.update { it.copy(sessionTime = millis) }
            }
        }

        viewModelScope.launch {
            settingsUseCases.getShortBreakTime().collect { minutes ->
                val millis = minutes?.toMillis() ?: 5.toMillis()
                _state.update { it.copy(shortBreakTime = millis) }
            }
        }

        viewModelScope.launch {
            settingsUseCases.getLongBreakTime().collect {minutes ->
                val millis = minutes?.toMillis() ?: 15.toMillis()
                _state.update { it.copy(longBreakTime = millis) }
            }
        }

        viewModelScope.launch {
            settingsUseCases.remindersOn().collect {
                _state.update { s -> s.copy(remindersOn = (it == 1)) }
            }
        }

        viewModelScope.launch {
            settingsUseCases.getFocusColor().collect {
                _state.update { s -> s.copy(focusColor = it) }
            }
        }

        viewModelScope.launch {
            settingsUseCases.getShortBreakColor().collect {
                _state.update { s -> s.copy(shortBreakColor = it) }
            }
        }

        viewModelScope.launch {
            settingsUseCases.getLongBreakColor().collect {
                _state.update { s -> s.copy(longBreakColor = it) }
            }
        }

        viewModelScope.launch {
            Timer.tickingTime.collect { time ->
                _state.update { it.copy(timer = time) }
            }
        }

        viewModelScope.launch {
            Timer.timerState.collect { timerState ->
                _state.update { it.copy(timerState = timerState) }
            }
        }
    }

    // --------------------------
    // TASK LOADING
    // --------------------------
    private fun loadTask(taskId: Int) {
        viewModelScope.launch {
            taskUseCases.getTask(taskId).collect { task ->
                _state.update {
                    it.copy(task = task, isLoading = false)
                }
            }
        }
    }

    // --------------------------
    // EXECUTION
    // --------------------------
    private fun startExecution() {
        val task = _state.value.task ?: return

        if (task.currentCycle == 0) {
            updateStartFromZero(task.id!!)
            return
        }

        when (task.current) {
            "Focus" -> handleFocus(task)
            "ShortBreak" -> handleShortBreak(task)
            "LongBreak" -> handleLongBreak(task)
        }
    }

    private fun updateStartFromZero(id: Int) {
        viewModelScope.launch {
            taskUseCases.updateTaskCycleNumber(id, 1)
            taskUseCases.updateCurrentSessionName(id, "Focus")
            taskUseCases.updateTaskInProgress(id, true)

            Timer.setTickingTime(state.value.sessionTime)
            Timer.start(update = { updateConsumed() }, executeTasks = { startExecution() })
        }
    }

    private fun handleFocus(task: Task) {
        val isLast = task.currentCycle == task.focusSessions

        val nextSession = if (isLast) "LongBreak" else "ShortBreak"
        val nextTime = if (isLast) state.value.longBreakTime else state.value.shortBreakTime

        //showNotificationFocus(task, isLast)

        updateTaskSession(task.id!!, nextSession, nextTime)
    }

    private fun handleShortBreak(task: Task) {
        //showShortBreakNotification(task)
        val nextCycle = task.currentCycle + 1

        viewModelScope.launch {
            taskUseCases.updateCurrentSessionName(task.id!!, "Focus")
            taskUseCases.updateTaskCycleNumber(task.id, nextCycle)
            taskUseCases.updateTaskInProgress(task.id, true)

            Timer.setTickingTime(state.value.sessionTime)
            Timer.start(update = { updateConsumed() }, executeTasks = { startExecution() })
        }
    }

    private fun handleLongBreak(task: Task) {
        //showLongBreakNotification(task)

        viewModelScope.launch {
            taskUseCases.updateTaskInProgress(task.id!!, false)
            taskUseCases.updateTaskCompleted(task.id, true)
            taskUseCases.updateTaskActive(task.id, false)
        }

        Timer.stop()
        Timer.reset()
    }

    // --------------------------
    // Session Handlers
    // --------------------------
    private fun updateTaskSession(taskId: Int, session: String, time: Long) {
        viewModelScope.launch {
            taskUseCases.updateCurrentSessionName(taskId, session)
            taskUseCases.updateTaskInProgress(taskId, true)

            Timer.setTickingTime(time)
            Timer.start(update = { updateConsumed() }, executeTasks = { startExecution() })
        }
    }

    private fun updateConsumed() {
        val t = state.value.task ?: return

        val time = Timer.tickingTime.value
        viewModelScope.launch {
            when (t.current) {
                "Focus" -> taskUseCases.updateConsumedFocusTime(t.id!!, time)
                "ShortBreak" -> taskUseCases.updateConsumedShortBreakTime(t.id!!, time)
                "LongBreak" -> taskUseCases.updateConsumedLongBreakTime(t.id!!, time)
            }
        }
    }

    // --------------------------
    // Move / Reset Actions
    // --------------------------
    private fun moveToNext() {
        // mismo manejo moderno
        startExecution()
    }

    private fun resetCurrentSession() {
        val t = state.value.task ?: return

        val time = when (t.current) {
            "Focus" -> state.value.sessionTime
            "ShortBreak" -> state.value.shortBreakTime
            "LongBreak" -> state.value.longBreakTime
            else -> 0
        }

        Timer.setTickingTime(time)
        Timer.start(update = { updateConsumed() }, executeTasks = { startExecution() })
    }

    fun handleTimerButton(timerState: TimerState) {
        when (timerState) {
            TimerState.Ticking -> Timer.pause()

            TimerState.Paused -> Timer.resume()

            TimerState.Idle -> {
                Timer.start(
                    update = { updateConsumed() },
                    executeTasks = { startExecution() }
                )
            }

            else -> Unit
        }
    }


    // --------------------------
    // Notifications helpers
    // --------------------------
    /*private fun showNotificationFocus(task: Task, isLast: Boolean) {
        if (!state.value.remindersOn) return

        val msg = if (isLast)
            "${task.currentCycle.formattedNumber()} focus completed, long break next"
        else
            "${task.currentCycle.formattedNumber()} focus completed, short break next"

        notificationManager.showNotification("[TASK] ${task.name}", msg)
    }

    private fun showShortBreakNotification(task: Task) {
        if (!state.value.remindersOn) return

        notificationManager.showNotification(
            "[TASK] ${task.name}",
            "Short break completed, focus next"
        )
    }

    private fun showLongBreakNotification(task: Task) {
        if (!state.value.remindersOn) return

        notificationManager.showNotification(
            "[TASK] ${task.name}",
            "Good job! Task completed 🎉"
        )
    }*/
}
