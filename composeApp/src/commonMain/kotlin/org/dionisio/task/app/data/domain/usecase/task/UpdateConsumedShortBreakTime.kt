package org.dionisio.task.app.data.domain.usecase.task

import org.dionisio.task.app.data.domain.repository.tasks.TasksRepository

class UpdateConsumedShortBreakTime(
    private val tasksRepository: TasksRepository
) {
    suspend operator fun invoke(id: Int, shortBreakTime: Long){
        tasksRepository.updateConsumedShortBreakTime(id, shortBreakTime)
    }
}