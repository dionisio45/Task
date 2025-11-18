package org.dionisio.task.app.data.domain.usecase.task

import org.dionisio.task.app.data.domain.repository.tasks.TasksRepository

class UpdateTaskActive(
    private val tasksRepository: TasksRepository
) {
    suspend operator fun invoke(id: Int, active: Boolean) {
        tasksRepository.updateTaskActive(id, active)
    }
}