package org.dionisio.task.app.data.domain.model

import kotlinx.datetime.LocalDateTime

data class Task(
    val id: Int? = null,
    val name: String,
    val description: String? = null,
    val type: String,
    val start: LocalDateTime,
    val date: LocalDateTime,
    val color: Long,
    val current: String,
    val focusSessions: Int,
    val currentCycle: Int,
    val completed: Boolean,
    val consumedFocusTime: Long,
    val consumedShortBreakTime: Long,
    val consumedLongBreakTime: Long,
    val inProgressTask: Boolean,
    val active: Boolean
)
