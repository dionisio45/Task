package org.dionisio.task.app.feature.taskprogress

import org.dionisio.task.app.data.domain.model.Task
import org.dionisio.task.app.utils.toMillis

data class TaskProgressState(
    val task: Task? = null,
    val sessionTime: Long = 25.toMillis(),
    val shortBreakTime: Long = 5.toMillis(),
    val longBreakTime: Long = 15.toMillis(),
    val remindersOn: Boolean = false,
    val focusColor: Long? = null,
    val shortBreakColor: Long? = null,
    val longBreakColor: Long? = null,
    val isLoading: Boolean = true,
    val timer: Long = 0L,
    val timerState: TimerState = TimerState.Idle,
)
