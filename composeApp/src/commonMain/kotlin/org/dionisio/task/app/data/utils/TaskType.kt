package org.dionisio.task.app.data.utils

import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import task.composeapp.generated.resources.Res
import task.composeapp.generated.resources.other
import task.composeapp.generated.resources.personal
import task.composeapp.generated.resources.study
import task.composeapp.generated.resources.work

data class TaskType @OptIn(ExperimentalResourceApi::class) constructor(
    val name: String,
    val icon: DrawableResource,
    val color: Long,
) {
    override fun toString(): String {
        return name
    }
}

@OptIn(ExperimentalResourceApi::class)
val taskTypes = listOf(
    TaskType(
        name = "Work",
        icon = Res.drawable.work,
        color = 0xFF3375fd,
    ),
    TaskType(
        name = "Study",
        icon = Res.drawable.study,
        color = 0xFFff686d,
    ),
    TaskType(
        name = "Personal",
        icon = Res.drawable.personal,
        color = 0xFF24c469,
    ),
    TaskType(
        name = "Other",
        icon = Res.drawable.other,
        color = 0xFF734efe,
    ),
)
