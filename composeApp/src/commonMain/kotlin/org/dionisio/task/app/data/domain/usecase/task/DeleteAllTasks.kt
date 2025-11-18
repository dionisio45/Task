package org.dionisio.task.app.data.domain.usecase.task

import org.dionisio.task.app.data.domain.repository.tasks.TasksRepository

class DeleteAllTasks(
    val tasksRepository: TasksRepository
) {
    suspend operator fun invoke(){
        tasksRepository.deleteAllTasks()
    }
}