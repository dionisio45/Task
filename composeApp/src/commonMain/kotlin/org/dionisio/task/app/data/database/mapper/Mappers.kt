package org.dionisio.task.app.data.database.mapper

import org.dionisio.task.app.data.domain.model.Task
import org.dionisio.task.app.core.utils.platform.DateTimeParser
import org.dionisio.task.app.data.database.local.entity.TaskEntity

fun TaskEntity.toDomain(): Task = Task(
    id = id,
    name = name,
    description = description,
    type = type,
    start = DateTimeParser.parse(start),
    date = DateTimeParser.parse(date),
    color = color,
    current = current,
    focusSessions = focusSessions,
    currentCycle = currentCycle,
    completed = completed,
    consumedFocusTime = consumedFocusTime,
    consumedShortBreakTime = consumedShortBreakTime,
    consumedLongBreakTime = consumedLongBreakTime,
    inProgressTask = inProgressTask,
    active = active
)

fun Task.toEntity(): TaskEntity = TaskEntity(
    id = this.id ?: 0,
    name = name,
    description = description,
    type = type,
    start = DateTimeParser.format(start),
    date = DateTimeParser.format(date),
    color = color,
    current = current,
    focusSessions = focusSessions,
    currentCycle = currentCycle,
    completed = completed,
    consumedFocusTime = consumedFocusTime,
    consumedShortBreakTime = consumedShortBreakTime,
    consumedLongBreakTime = consumedLongBreakTime,
    inProgressTask = inProgressTask,
    active = active
)
