package org.dionisio.task.app.data.domain.usecase.task

import org.dionisio.task.app.data.domain.repository.tasks.TasksRepository

class UpdateAllTasksActiveStatusToInactive(
    private val tasksRepository: TasksRepository
) {
    suspend operator fun invoke() {
        tasksRepository.updateAllTasksActiveStatusToInactive()
    }
}