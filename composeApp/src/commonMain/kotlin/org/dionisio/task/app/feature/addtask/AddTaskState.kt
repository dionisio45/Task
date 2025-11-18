package org.dionisio.task.app.feature.addtask

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import org.dionisio.task.app.data.domain.model.Task
import org.dionisio.task.app.data.utils.TaskType
import org.dionisio.task.app.data.utils.taskTypes
import org.dionisio.task.app.utils.today

data class AddTaskState(
    // Task
    val id: Int? = null,
    val name: String = "",
    val description: String = "",
    val type: TaskType = taskTypes.last(),
    val taskDate: LocalDateTime = today(),
    val startTime: LocalTime = today().time,
    val endTime: LocalTime = today().time,
    val color: Long = 0xFF4CAF50,
    val current: String = "session",
    val focusSessions: Int = 1,
    val currentCycle: Int = 1,
    val completed: Boolean = false,
    val consumedFocusTime: Long = 0,
    val consumedShortBreakTime: Long = 0,
    val consumedLongBreakTime: Long = 0,
    val inProgressTask: Boolean = false,
    val active: Boolean = true,
    val showStartTimeInputDialog: Boolean = false,
    val showEndTimeDialog: Boolean = false,
    val showTaskDatePickerDialog: Boolean = false,
    val isLoading: Boolean = false,
    // Settings
    val hourFormat: Int = 24,
    val sessionTime: Int = 25,
    val shortBreakTime: Int = 5,
    val longBreakTime: Int = 15,

)