package org.dionisio.task.app.feature.home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.dionisio.task.app.data.domain.model.Task
import org.dionisio.task.app.data.presentation.component.TaskCard
import org.dionisio.task.app.data.presentation.theme.LongBreakColor
import org.dionisio.task.app.data.presentation.theme.SessionColor
import org.dionisio.task.app.data.presentation.theme.ShortBreakColor
import org.dionisio.task.app.feature.home.TasksState
import org.dionisio.task.app.feature.taskprogress.TimerState
import org.dionisio.task.app.data.utils.SessionType
import org.dionisio.task.app.utils.pickFirstName
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@Composable
fun HomeScreenContent(
    tasksState: TasksState,
    timerState: TimerState,
    tickingTime: Long,
    hourFormat: Int,
    sessionTime: Int,
    shortBreakTime: Int,
    longBreakTime: Int,
    username: String?,
    onClickTask: (Task) -> Unit,
    onClickSeeAllTasks: (String) -> Unit,
    onClickTaskOptions: (Task) -> Unit,
    focusTimeColor: Long?,
    shortBreakColor: Long?,
    longBreakColor: Long?,
    onClickActiveTaskOptions: (Task) -> Unit,
    onClickAddTask: () -> Unit,
) {
    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
        ) {
            when (tasksState) {
                TasksState.Loading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                    )
                }

                is TasksState.Success -> {
                    val tasks = tasksState.tasks.sortedByDescending { it.completed.not() }
                    val overdueTasks = tasksState.overdueTasks
                    val activeTask = tasks.firstOrNull { it.active }

                    LazyColumn(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        if (activeTask != null) {
                            item {
                                val containerColor = when (activeTask.current.sessionType()) {
                                    SessionType.Focus -> {
                                        if (focusTimeColor == null || focusTimeColor == 0L) {
                                            Color(SessionColor)
                                        } else {
                                            Color(
                                                focusTimeColor,
                                            )
                                        }
                                    }

                                    SessionType.LongBreak -> {
                                        if (longBreakColor == null || longBreakColor == 0L) {
                                            Color(LongBreakColor)
                                        } else {
                                            Color(
                                                longBreakColor,
                                            )
                                        }
                                    }

                                    SessionType.ShortBreak -> {
                                        if (shortBreakColor == null || shortBreakColor == 0L) {
                                            Color(ShortBreakColor)
                                        } else {
                                            Color(
                                                shortBreakColor,
                                            )
                                        }
                                    }
                                }
                                ActiveTaskCard(
                                    task = activeTask,
                                    onClick = onClickTask,
                                    containerColor = containerColor,
                                    onClickTaskOptions = onClickActiveTaskOptions,
                                    timerState = timerState,
                                    tickingTime = tickingTime,
                                )
                            }
                        }

                        item {
                            Text(
                                text = "Hello, ${username?.pickFirstName()}!",
                                style = MaterialTheme.typography.displaySmall,
                            )
                        }

                        if (tasks.isNotEmpty() && tasks.all { it.completed }.not()) {
                            item {
                                TodayTaskProgressCard(tasks)
                            }
                        }

                        if (overdueTasks.isNotEmpty()) {
                            item {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                ) {
                                    Text(
                                        text = "Overdue Tasks (${overdueTasks.size})",
                                        style = MaterialTheme.typography.titleLarge.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.error,
                                        ),
                                    )
                                    if (overdueTasks.size > 3) {
                                        Text(
                                            modifier = Modifier.clickable {
                                                onClickSeeAllTasks("overdue")
                                            },
                                            text = "See All",
                                            style = MaterialTheme.typography.labelLarge.copy(
                                                fontWeight = FontWeight.SemiBold,
                                                color = MaterialTheme.colorScheme.error,
                                                fontSize = 16.sp,
                                            ),
                                        )
                                    }
                                }
                            }

                            items(
                                items = tasksState.overdueTasks.take(3),
                                key = { it.id ?: 0 },
                            ) {
                                TaskCard(
                                    type = "overdue",
                                    task = it,
                                    onClick = onClickTask,
                                    onShowTaskOption = onClickTaskOptions,
                                    hourFormat = hourFormat,
                                    focusSessions = it.focusSessions,
                                    sessionTime = sessionTime,
                                    shortBreakTime = shortBreakTime,
                                    longBreakTime = longBreakTime,
                                )
                            }
                        }

                        if (tasks.isNotEmpty()) {
                            item {
                                Spacer(modifier = Modifier.height(16.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                ) {
                                    Text(
                                        text = "Today's Tasks (${tasks.size})",
                                        style = MaterialTheme.typography.titleLarge.copy(
                                            fontWeight = FontWeight.Bold,
                                        ),
                                    )
                                    if (tasks.size > 3) {
                                        Text(
                                            modifier = Modifier.clickable {
                                                onClickSeeAllTasks("today")
                                            },
                                            text = "See All",
                                            style = MaterialTheme.typography.labelLarge.copy(
                                                fontWeight = FontWeight.SemiBold,
                                                color = MaterialTheme.colorScheme.primary,
                                                fontSize = 16.sp,
                                            ),
                                        )
                                    }
                                }
                            }
                        }

                        if (tasks.all { it.completed }.not()) {
                            items(
                                items = tasks.take(3),
                                key = { it.id ?: 0 },
                            ) {
                                TaskCard(
                                    type = "today",
                                    task = it,
                                    onClick = onClickTask,
                                    onShowTaskOption = onClickTaskOptions,
                                    hourFormat = hourFormat,
                                    focusSessions = it.focusSessions,
                                    sessionTime = sessionTime,
                                    shortBreakTime = shortBreakTime,
                                    longBreakTime = longBreakTime,
                                )
                            }
                        }


                        if ((tasks.all { it.completed } || tasks.isEmpty()) && overdueTasks.isEmpty()) {
                            item {
                                StartOrCompletedTaskComponent(
                                    tasks = tasks,
                                    onClickAddTask = onClickAddTask
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalTime::class)
@Preview(showBackground = true, name = "HomeScreenContent Preview")
@Composable
fun HomeScreenContentPreview() {
    val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

    val mockTasksState = TasksState.Success(
        tasks = listOf(
            Task(
                id = 1,
                name = "Revisar informes",
                description = "Completar reporte semanal",
                type = "work",
                start = now,
                date = now,
                color = 0xFF00BCD4,
                current = "Focus",
                focusSessions = 4,
                currentCycle = 2,
                completed = false,
                consumedFocusTime = 15,
                consumedShortBreakTime = 5,
                consumedLongBreakTime = 0,
                inProgressTask = true,
                active = true
            ),
            Task(
                id = 2,
                name = "Planificar proyecto",
                description = "Organizar fases del desarrollo",
                type = "planning",
                start = now,
                date = now,
                color = 0xFF4CAF50,
                current = "Break",
                focusSessions = 3,
                currentCycle = 3,
                completed = true,
                consumedFocusTime = 25,
                consumedShortBreakTime = 5,
                consumedLongBreakTime = 10,
                inProgressTask = false,
                active = false
            )
        ),
        overdueTasks = listOf(
            Task(
                id = 3,
                name = "Enviar correos pendientes",
                description = "Correos de seguimiento a clientes",
                type = "email",
                start = now,
                date = now,
                color = 0xFFFF9800,
                current = "Focus",
                focusSessions = 2,
                currentCycle = 1,
                completed = false,
                consumedFocusTime = 10,
                consumedShortBreakTime = 5,
                consumedLongBreakTime = 0,
                inProgressTask = false,
                active = false
            )
        )
    )

    HomeScreenContent(
        tasksState = mockTasksState,
        timerState = TimerState.Idle, // ✅ Usa el estado correcto
        tickingTime = 1200L,
        hourFormat = 24,
        sessionTime = 25,
        shortBreakTime = 5,
        longBreakTime = 15,
        username = "Dionisio",
        focusTimeColor = 0xFF00BCD4,
        shortBreakColor = 0xFF4CAF50,
        longBreakColor = 0xFFFF9800,
        onClickTask = {},
        onClickSeeAllTasks = {},
        onClickTaskOptions = {},
        onClickActiveTaskOptions = {},
        onClickAddTask = {}
    )
}
