package org.dionisio.task.app.feature.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.dionisio.task.app.data.domain.model.Task
import org.dionisio.task.app.data.presentation.component.BloomButton
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import task.composeapp.generated.resources.Res
import task.composeapp.generated.resources.il_completed
import task.composeapp.generated.resources.il_empty
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@Composable
fun StartOrCompletedTaskComponent(
    tasks: List<Task>,
    onClickAddTask: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Image(
            modifier = Modifier
                .size(300.dp)
                .align(CenterHorizontally),
            painter = painterResource(if (tasks.isEmpty()) Res.drawable.il_empty else Res.drawable.il_completed),
            contentDescription = null,
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(CenterHorizontally),
            style = MaterialTheme.typography.titleSmall.copy(
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
            ),
            text = if (tasks.isEmpty()) {
                "Start your day productively! Add your first task."
            } else if (tasks.all { it.completed }) {
                "Great job! You've finished all your tasks for today."
            } else {
                ""
            },
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth(),
            text = if (tasks.isEmpty()) {
                "To add a task, simply tap the '+' button on the screen. Fill in the task details and tap 'Save'."
            } else if (tasks.all { it.completed }) {
                "Now, take some time to have fun, recharge, maybe do some exercise, and consider opening your calendar to plan for tomorrow's tasks. Keep up the fantastic work!"
            } else {
                ""
            },
            style = MaterialTheme.typography.labelLarge.copy(
                fontSize = 14.sp,
            ),
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(24.dp))
        if (tasks.isEmpty()) {
            BloomButton(
                onClick = onClickAddTask,
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 24.dp),
                    text = "Add Your First Task",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold,
                    ),
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "Empty Tasks Preview")
@Composable
fun StartOrCompletedTaskComponentEmptyPreview() {
    MaterialTheme {
        StartOrCompletedTaskComponent(
            tasks = emptyList(),
            onClickAddTask = {}
        )
    }
}

@OptIn(ExperimentalTime::class)
@Preview(showBackground = true, name = "All Completed Tasks Preview")
@Composable
fun StartOrCompletedTaskComponentCompletedPreview() {
    val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

    val completedTasks = listOf(
        Task(
            id = 1,
            name = "Revisar informes",
            description = "Tarea completada",
            type = "work",
            start = now,
            date = now,
            color = 0xFF00BCD4,
            current = "Focus",
            focusSessions = 4,
            currentCycle = 4,
            completed = true,
            consumedFocusTime = 25,
            consumedShortBreakTime = 5,
            consumedLongBreakTime = 0,
            inProgressTask = false,
            active = false
        ),
        Task(
            id = 2,
            name = "Enviar correos",
            description = "Tarea completada",
            type = "communication",
            start = now,
            date = now,
            color = 0xFF4CAF50,
            current = "Break",
            focusSessions = 3,
            currentCycle = 3,
            completed = true,
            consumedFocusTime = 20,
            consumedShortBreakTime = 5,
            consumedLongBreakTime = 10,
            inProgressTask = false,
            active = false
        )
    )

    MaterialTheme {
        StartOrCompletedTaskComponent(
            tasks = completedTasks,
            onClickAddTask = {}
        )
    }
}




