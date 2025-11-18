package org.dionisio.task.app.data.domain.usecase.task

import org.dionisio.task.app.data.domain.repository.tasks.TasksRepository

class UpdateTaskInProgress(
    private val tasksRepository: TasksRepository
) {
    suspend operator fun invoke(id: Int, inProgressTask: Boolean) {
        tasksRepository.updateTaskInProgress(id, inProgressTask)
    }
}