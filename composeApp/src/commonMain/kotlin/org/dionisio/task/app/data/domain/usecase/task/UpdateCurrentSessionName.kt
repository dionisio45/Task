package org.dionisio.task.app.data.domain.usecase.task

import org.dionisio.task.app.data.domain.repository.tasks.TasksRepository

class UpdateCurrentSessionName(
    private  val tasksRepository: TasksRepository
) {
    suspend operator fun invoke(id: Int, current: String) {
        tasksRepository.updateCurrentSessionName(id, current)
    }
}