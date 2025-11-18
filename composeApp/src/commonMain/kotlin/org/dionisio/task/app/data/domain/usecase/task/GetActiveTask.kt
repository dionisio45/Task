package org.dionisio.task.app.data.domain.usecase.task

import org.dionisio.task.app.data.domain.model.Task
import org.dionisio.task.app.data.domain.repository.tasks.TasksRepository
import kotlinx.coroutines.flow.Flow

class GetActiveTask(
    private val tasksRepository: TasksRepository
) {
    suspend operator fun invoke(): Flow<Task?> {
        return tasksRepository.getActiveTask()
    }
}