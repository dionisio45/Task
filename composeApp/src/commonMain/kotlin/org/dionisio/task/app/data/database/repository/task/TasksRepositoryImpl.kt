package org.dionisio.task.app.data.database.repository.task

import org.dionisio.task.app.data.database.dao.TaskDao
import org.dionisio.task.app.data.database.mapper.toDomain
import org.dionisio.task.app.data.database.mapper.toEntity
import org.dionisio.task.app.data.domain.model.Task
import org.dionisio.task.app.data.domain.repository.tasks.TasksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TasksRepositoryImpl(
    private val dao: TaskDao
) : TasksRepository {
    override suspend fun addTask(task: Task) {
        dao.addTask(task.toEntity())
    }

    override fun getTasks(): Flow<List<Task>> {
        return dao.getTasks().map { list -> list.map { it.toDomain() } }
    }

    override fun getTask(id: Int): Flow<Task?> {
        return dao.getTask(id).map { it?.toDomain() }
    }

    override fun getActiveTask(): Flow<Task?> {
        return dao.getActiveTask().map { it?.toDomain() }
    }

    /*override suspend fun updateTask(task: Task) {
        dao.updateTask(task.toEntity())
    }*/

    override suspend fun deleteTask(id: Int) {
        dao.deleteTask(id)
    }

    override suspend fun deleteAllTasks() {
        dao.deleteAllTasks()
    }

    override suspend fun updateConsumedFocusTime(id: Int, focusTime: Long) {
        dao.updateConsumedFocusTime(id, focusTime)
    }

    override suspend fun updateConsumedShortBreakTime(id: Int, shortBreakTime: Long) {
        dao.updateConsumedShortBreakTime(id, shortBreakTime)
    }

    override suspend fun updateConsumedLongBreakTime(id: Int, longBreakTime: Long) {
        dao.updateConsumedLongBreakTime(id, longBreakTime)
    }

    override suspend fun updateTaskInProgress(id: Int, inProgressTask: Boolean) {
        dao.updateInProgressTask(id, inProgressTask)
    }

    override suspend fun updateTaskCompleted(id: Int, completed: Boolean) {
        dao.updateTaskCompleted(id, completed)
    }

    override suspend fun updateCurrentSessionName(id: Int, current: String) {
        dao.updateCurrentSessionName(id, current)
    }

    override suspend fun updateTaskCycleNumber(id: Int, cycle: Int) {
        dao.updateTaskCycleNumber(id, cycle)
    }

    override suspend fun updateTaskActive(id: Int, active: Boolean) {
        dao.updateTaskActiveStatus(id, active)
    }

    override suspend fun updateAllTasksActiveStatusToInactive() {
        dao.updateAllTasksActiveStatusToInactive()
    }
}
