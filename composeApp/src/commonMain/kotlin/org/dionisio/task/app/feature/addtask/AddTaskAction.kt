package org.dionisio.task.app.feature.addtask

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import org.dionisio.task.app.data.domain.model.Task
import org.dionisio.task.app.data.utils.TaskType

sealed interface AddTaskAction {
    data class OnSetName(val name: String) : AddTaskAction
    data class OnSetDescription(val description: String) : AddTaskAction
    data class OnSetType(val type: TaskType) : AddTaskAction
    data class OnSetDate(val date: LocalDateTime) : AddTaskAction
    data class OnSetStartTime(val time: LocalTime) : AddTaskAction
    data class OnSetEndTime(val time: LocalTime) : AddTaskAction
    data class OnSetFocusSessions(val sessions: Int) : AddTaskAction
    data class OnShowStartTimeInputDialog(val show: Boolean) : AddTaskAction
    data class OnShowEndTimeDialog(val show: Boolean) : AddTaskAction
    data class OnTaskShowDatePickerDialog(val show: Boolean) : AddTaskAction

    //data class
    object OnIncrementSessions : AddTaskAction
    object OnDecrementSessions : AddTaskAction
    data class OnLoadTask(val taskId: Int?) : AddTaskAction
    data class OnAddOrUpdateTask(val task: Task) : AddTaskAction
   // object SaveTask : AddTaskAction
    object OnReset : AddTaskAction
}