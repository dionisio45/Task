package org.dionisio.task.app.data.domain.usecase.task

import org.dionisio.task.app.data.domain.repository.tasks.TasksRepository

class UpdateConsumedFocusTime(
    private val tasksRepository: TasksRepository
) {
    suspend operator fun invoke(id: Int, focusTime: Long){
        tasksRepository.updateConsumedFocusTime(id, focusTime)
    }
}