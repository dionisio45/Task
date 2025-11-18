package org.dionisio.task.app.feature.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.dionisio.task.app.data.presentation.component.TaskProgress
import org.dionisio.task.app.utils.taskCompleteMessage
import org.dionisio.task.app.utils.taskCompletionPercentage
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.dionisio.task.app.data.domain.model.Task
import kotlin.time.ExperimentalTime

@Composable
fun TodayTaskProgressCard(tasks: List<Task>) {
    Card {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            TaskProgress(
                mainColor = MaterialTheme.colorScheme.primary,
                percentage = taskCompletionPercentage(tasks).toFloat(),
                counterColor = MaterialTheme.colorScheme.onSurface,
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    text = taskCompleteMessage(tasks),
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.SemiBold,
                    ),
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${tasks.filter { it.completed }.size} of ${tasks.size} tasks completed",
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = MaterialTheme.colorScheme.onSurface,
                    ),
                )
            }
        }
    }
}

@OptIn(ExperimentalTime::class)
@Preview(showBackground = true)
@Composable
private fun TodayTaskProgressCardPreview() {
    val now: LocalDateTime = kotlin.time.Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    val sampleTasks = listOf(
        org.dionisio.task.app.data.domain.model.Task(
            id = 1,
            name = "Write report",
            description = "Finish the monthly report",
            type = "Work",
            start = now,
            date = now,
            color = 0xFF6200EE,
            current = "",
            focusSessions = 1,
            currentCycle = 1,
            completed = false,
            consumedFocusTime = 0L,
            consumedShortBreakTime = 0L,
            consumedLongBreakTime = 0L,
            inProgressTask = false,
            active = true
        ),
        Task(
            id = 2,
            name = "Check emails",
            description = null,
            type = "Work",
            start = now,
            date = now,
            color = 0xFF03DAC5,
            current = "",
            focusSessions = 1,
            currentCycle = 1,
            completed = true,
            consumedFocusTime = 0L,
            consumedShortBreakTime = 0L,
            consumedLongBreakTime = 0L,
            inProgressTask = false,
            active = true
        ),
        Task(
            id = 3,
            name = "Team meeting",
            description = "Discuss project",
            type = "Meeting",
            start = now,
            date = now,
            color = 0xFFFF5722,
            current = "",
            focusSessions = 1,
            currentCycle = 1,
            completed = false,
            consumedFocusTime = 0L,
            consumedShortBreakTime = 0L,
            consumedLongBreakTime = 0L,
            inProgressTask = true,
            active = true
        )
    )

    MaterialTheme {
        TodayTaskProgressCard(tasks = sampleTasks)
    }
}