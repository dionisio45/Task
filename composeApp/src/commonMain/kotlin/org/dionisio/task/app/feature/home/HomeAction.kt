package org.dionisio.task.app.feature.home

import org.dionisio.task.app.data.domain.model.Task

sealed interface HomeAction {
    data class OnOpenBottomSheet(val value: Boolean) : HomeAction
    data class OnSelectTask(val task: Task?) : HomeAction
    data class OnDeleteSelectedTask(val task: Task) : HomeAction
    data class OnPushTaskToTomorrow(val task: Task) : HomeAction
    data class OnPushTaskToToday(val task: Task) : HomeAction
    data class OnMarkTaskAsCompleted(val task: Task) : HomeAction
    data class OnToggleReminder(val value: Int) : HomeAction

}