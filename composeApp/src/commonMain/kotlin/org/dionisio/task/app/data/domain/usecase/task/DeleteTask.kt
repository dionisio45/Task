package org.dionisio.task.app.data.domain.usecase.task

import org.dionisio.task.app.data.domain.repository.tasks.TasksRepository

class DeleteTask(
    private val tasksRepository: TasksRepository
) {
    suspend operator fun invoke(id: Int) {
        tasksRepository.deleteTask(id)
    }
}