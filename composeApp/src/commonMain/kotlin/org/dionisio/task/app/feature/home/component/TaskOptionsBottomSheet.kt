package org.dionisio.task.app.feature.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Done
import androidx.compose.material.icons.outlined.EditCalendar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalDateTime
import org.dionisio.task.app.data.domain.model.Task
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskOptionsBottomSheet(
    bottomSheetState: SheetState,
    onClickCancel: (task: Task) -> Unit,
    onClickDelete: (task: Task) -> Unit,
    onClickPushToTomorrow: (task: Task) -> Unit,
    task: Task,
    onDismissRequest: () -> Unit,
    onClickMarkAsCompleted: (task: Task) -> Unit,
    onClickEditTask: (task: Task) -> Unit,
    type: String,
    onClickPushToToday: (task: Task) -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = bottomSheetState,
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {
            Option(
                icon = Icons.Default.Edit,
                text = "Edit Task",
                onClick = {
                    onClickEditTask(task)
                    onDismissRequest()
                },
            )
            Option(
                icon = Icons.Outlined.EditCalendar,
                text = if (type == "overdue") "Push to Today" else "Push to Tomorrow",
                onClick = {
                    if (type == "overdue") onClickPushToToday(task) else onClickPushToTomorrow(task)
                    onDismissRequest()
                },
            )
            Option(
                icon = Icons.Outlined.Done,
                text = "Mark as Completed",
                onClick = {
                    onClickMarkAsCompleted(task)
                    onDismissRequest()
                },
            )
            Option(
                icon = Icons.Outlined.Delete,
                text = "Delete Task",
                onClick = {
                    onClickDelete(task)
                    onDismissRequest()
                },
            )
            Option(
                icon = Icons.Outlined.Close,
                text = "Cancel",
                onClick = {
                    onClickCancel(task)
                    onDismissRequest()
                },
            )
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun TaskOptionsBottomSheetPreview() {
    val dummyStart = LocalDateTime(2025, 10, 22, 10, 0)
    val dummyDate = LocalDateTime(2025, 10, 22, 12, 0)

    // ✅ Usa rememberModalBottomSheetState en lugar de SheetState directamente
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    // Dummy Task
    val dummyTask = Task(
        id = 1,
        name = "Study Kotlin",
        description = "Learn Kotlin Multiplatform",
        type = "normal",
        start = dummyStart,
        date = dummyDate,
        color = 0xFF6200EE,
        current = "Focus",
        focusSessions = 2,
        currentCycle = 1,
        completed = false,
        consumedFocusTime = 0L,
        consumedShortBreakTime = 0L,
        consumedLongBreakTime = 0L,
        inProgressTask = false,
        active = true
    )

    TaskOptionsBottomSheet(
        bottomSheetState = bottomSheetState,
        task = dummyTask,
        type = dummyTask.type,
        onClickCancel = {},
        onClickDelete = {},
        onClickPushToTomorrow = {},
        onDismissRequest = {},
        onClickMarkAsCompleted = {},
        onClickEditTask = {},
        onClickPushToToday = {}
    )
}
