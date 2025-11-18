package org.dionisio.task.app.data.domain.usecase.task

import org.dionisio.task.app.data.domain.repository.tasks.TasksRepository

class UpdateTaskCompleted(
    private val tasksRepository: TasksRepository
) {
    suspend operator fun invoke(id: Int, completed: Boolean) {
        tasksRepository.updateTaskCompleted(id, completed)
    }
}