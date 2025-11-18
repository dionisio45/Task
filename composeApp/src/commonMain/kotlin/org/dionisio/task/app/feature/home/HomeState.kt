package org.dionisio.task.app.feature.home

import org.dionisio.task.app.data.domain.model.Task

data class HomeState(
    val isBottomSheetOpen: Boolean = false,
    val hourFormat: Int = 24,
    val sessionTime: Int = 0,
    val shortBreakTime: Int = 0,
    val longBreakTime: Int = 0,
    val remindersOn: ReminderState = ReminderState.Loading,
    val selectedTask: Task? = null,
    val tasks: TasksState = TasksState.Loading,
    val username: String? = null,
    val shortBreakColor: Long? = null,
    val longBreakColor: Long? = null,
    val focusColor: Long? = null
)
