package org.dionisio.task.app.core.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import org.dionisio.task.app.data.database.dao.TaskDao
import org.dionisio.task.app.data.database.local.entity.TaskEntity

@Database(
    entities = [
        TaskEntity::class
    ],
    version = 1
)

@TypeConverters(TaskTypeConverters::class)

@ConstructedBy(TaskDatabaseConstructor::class)
abstract class TaskDatabase : RoomDatabase() {

    abstract val taskDao: TaskDao

    companion object {
        const val DB_NAME = "Task.db"
    }
}

