package org.dionisio.task.app.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import org.dionisio.task.app.data.database.local.entity.TaskEntity
import kotlinx.coroutines.flow.Flow
@Dao
interface TaskDao {
    @Upsert
    suspend fun addTask(task: TaskEntity): Long  // ← Retorna Long (ID de la fila insertada/actualizada)

    @Query("SELECT * FROM Task ORDER BY date DESC")
    fun getTasks(): Flow<List<TaskEntity>>  // ← Ya está bien (Flow no necesita suspend)

    @Query("SELECT * FROM Task WHERE id = :id")
    fun getTask(id: Int): Flow<TaskEntity?>  // ← Ya está bien (Flow)

    @Query("SELECT * FROM Task WHERE active = 1")
    fun getActiveTask(): Flow<TaskEntity?>  // ← Ya está bien (Flow)

    @Query("DELETE FROM Task WHERE id = :id")
    suspend fun deleteTask(id: Int): Int  // ← Retorna Int (filas afectadas), ahora suspend

    @Query("DELETE FROM Task")
    suspend fun deleteAllTasks(): Int  // ← Retorna Int

    @Query("UPDATE Task SET consumedFocusTime = :focusTime WHERE id = :id")
    suspend fun updateConsumedFocusTime(id: Int, focusTime: Long): Int  // ← Retorna Int

    @Query("UPDATE Task SET consumedShortBreakTime = :shortBreakTime WHERE id = :id")
    suspend fun updateConsumedShortBreakTime(id: Int, shortBreakTime: Long): Int  // ← Retorna Int

    @Query("UPDATE Task SET consumedLongBreakTime = :longBreakTime WHERE id = :id")
    suspend fun updateConsumedLongBreakTime(id: Int, longBreakTime: Long): Int  // ← Retorna Int

    @Query("UPDATE Task SET inProgressTask = :inProgressTask WHERE id = :id")
    suspend fun updateInProgressTask(id: Int, inProgressTask: Boolean): Int  // ← Retorna Int

    @Query("UPDATE Task SET `current` = :current WHERE id = :id")
    suspend fun updateCurrentSessionName(id: Int, current: String): Int  // ← Retorna Int

    @Query("UPDATE Task SET completed = :completed WHERE id = :id")
    suspend fun updateTaskCompleted(id: Int, completed: Boolean): Int  // ← Retorna Int

    @Query("UPDATE Task SET currentCycle = :cycle WHERE id = :id")
    suspend fun updateTaskCycleNumber(id: Int, cycle: Int): Int  // ← Retorna Int

    @Query("UPDATE Task SET active = :active WHERE id = :id")
    suspend fun updateTaskActiveStatus(id: Int, active: Boolean): Int  // ← Retorna Int

    @Query("UPDATE Task SET active = 0")
    suspend fun updateAllTasksActiveStatusToInactive(): Int  // ← Retorna Int, ahora suspend
}