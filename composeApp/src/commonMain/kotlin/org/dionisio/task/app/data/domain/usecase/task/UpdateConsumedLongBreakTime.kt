package org.dionisio.task.app.data.domain.usecase.task

import org.dionisio.task.app.data.domain.repository.tasks.TasksRepository

class UpdateConsumedLongBreakTime(
    private val tasksRepository: TasksRepository
) {
    suspend operator fun invoke(id: Int, longBreakTime: Long) {
        tasksRepository.updateConsumedLongBreakTime(id, longBreakTime)
    }
}