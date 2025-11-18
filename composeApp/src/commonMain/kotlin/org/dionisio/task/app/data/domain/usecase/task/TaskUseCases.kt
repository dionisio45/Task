package org.dionisio.task.app.data.domain.usecase.task

data class TaskUseCases(
    val getTasks : GetTasks,
    val getTask : GetTask,
    val addTask: AddTask,
    val deleteTask: DeleteTask,
    val deleteAllTasks: DeleteAllTasks,
    val updateConsumedFocusTime: UpdateConsumedFocusTime,
    val updateConsumedShortBreakTime: UpdateConsumedShortBreakTime,
    val updateConsumedLongBreakTime: UpdateConsumedLongBreakTime,
    val updateTaskInProgress: UpdateTaskInProgress,
    val updateTaskCompleted: UpdateTaskCompleted,
    val updateCurrentSessionName: UpdateCurrentSessionName,
    val updateTaskCycleNumber: UpdateTaskCycleNumber,
    val getActiveTask: GetActiveTask,
    val updateTaskActive: UpdateTaskActive,
    val updateAllTasksActiveStatusToInactive: UpdateAllTasksActiveStatusToInactive
)
