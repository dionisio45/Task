package org.dionisio.task.app.data.domain.usecase.task

import org.dionisio.task.app.data.domain.model.Task
import org.dionisio.task.app.data.domain.repository.tasks.TasksRepository

class AddTask (
    private val tasksRepository: TasksRepository
) {
    suspend operator fun invoke(task: Task) {
        tasksRepository.addTask(task)
    }
}