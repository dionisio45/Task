package org.dionisio.task.app.data.domain.usecase.task

import org.dionisio.task.app.data.domain.model.Task
import org.dionisio.task.app.data.domain.repository.tasks.TasksRepository
import kotlinx.coroutines.flow.Flow

class GetTasks(
    private val tasksRepository: TasksRepository
) {
    operator fun invoke(): Flow<List<Task>> {
        return tasksRepository.getTasks()
    }
}