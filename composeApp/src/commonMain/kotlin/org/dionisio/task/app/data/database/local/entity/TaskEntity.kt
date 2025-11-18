package org.dionisio.task.app.data.database.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val description: String? = null,
    val type: String,
    val start: String,
    val color: Long,
    val current: String,
    val date: String,
    val focusSessions: Int,
    val currentCycle: Int,
    val completed: Boolean,
    val consumedFocusTime: Long,
    val consumedShortBreakTime: Long,
    val consumedLongBreakTime: Long,
    val inProgressTask: Boolean,
    val active: Boolean,
)