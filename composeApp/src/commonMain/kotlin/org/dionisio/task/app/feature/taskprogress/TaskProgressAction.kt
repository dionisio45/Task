package org.dionisio.task.app.feature.taskprogress

sealed interface TaskProgressAction {
    data class LoadTask(val taskId: Int) : TaskProgressAction
    data class TimerButtonClicked(val timerState: TimerState) : TaskProgressAction
    data object Start : TaskProgressAction
    data object MoveToNextSession : TaskProgressAction
    data object ResetSession : TaskProgressAction
}
