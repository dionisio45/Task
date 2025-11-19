package org.dionisio.task.app.feature.taskprogress.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.dionisio.task.app.data.domain.model.Task
import org.dionisio.task.app.data.presentation.component.BloomTimerControls
import org.dionisio.task.app.data.presentation.component.BloomTopAppBar
import org.dionisio.task.app.data.presentation.component.TaskProgress
import org.dionisio.task.app.data.utils.SessionType
import org.dionisio.task.app.feature.taskprogress.Timer
import org.dionisio.task.app.feature.taskprogress.TimerState
import org.dionisio.task.app.utils.durationInMinutes
import org.dionisio.task.app.utils.sessionType
import org.dionisio.task.app.utils.toMinutes
import org.dionisio.task.app.utils.toPercentage
import org.dionisio.task.app.utils.toTimer
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FocusTimeScreenContent(
    focusTime: Long,
    shortBreakTime: Long,
    longBreakTime: Long,
    containerColor: Color,
    timerValue: Long,
    timerState: TimerState,
    task: Task?,
    snackbarHostState: SnackbarHostState,
    onClickNavigateBack: () -> Unit,
    onClickAction: (state: TimerState) -> Unit,
    onClickNext: () -> Unit,
    onClickReset: () -> Unit,
) {
    Scaffold(
        containerColor = containerColor,
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            BloomTopAppBar(
                hasBackNavigation = true,
                navigationIcon = {
                    IconButton(onClick = onClickNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                            contentDescription = "Add Task Back Button",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = containerColor,
                ),
            )
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier.padding(paddingValues).fillMaxSize(),
        ) {
            if (task == null) {
                Text(
                    modifier = Modifier.align(Alignment.Center),
                    text = "Task not found",
                )
            } else {
                LazyColumn(
                    modifier = Modifier.padding(PaddingValues(horizontal = 16.dp)),
                ) {
                    item {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                ) {
                                    Text(
                                        modifier = Modifier.fillMaxWidth(.85f),
                                        text = task.name,
                                        style = MaterialTheme.typography.titleSmall,
                                        maxLines = 3,
                                        overflow = TextOverflow.Ellipsis,
                                    )

                                    Text(
                                        text = buildAnnotatedString {
                                            withStyle(
                                                style = SpanStyle(
                                                    fontWeight = FontWeight.SemiBold,
                                                    fontSize = 18.sp,
                                                ),
                                            ) {
                                                append("${task.currentCycle}")
                                            }
                                            append("/${task.focusSessions}")
                                        },
                                    )
                                }
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                ) {
                                    Text(
                                        text = "Total: ${
                                            task.durationInMinutes(
                                                sessionTime = focusTime.toMinutes(),
                                                shortBreakTime = shortBreakTime.toMinutes(),
                                                longBreakTime = longBreakTime.toMinutes(),
                                                focusSessions = task.focusSessions,
                                            )
                                        } minutes",
                                        style = MaterialTheme.typography.bodySmall,
                                    )
                                    Text(
                                        text = when (task.current.sessionType()) {
                                            SessionType.Focus -> "${focusTime.toMinutes()} min"
                                            SessionType.ShortBreak -> "${shortBreakTime.toMinutes()} min"
                                            SessionType.LongBreak -> "${longBreakTime.toMinutes()} min"
                                        },
                                    )
                                }
                            }
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(32.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                        ) {
                            TaskProgress(
                                percentage = timerValue.toPercentage(
                                    when (task.current.sessionType()) {
                                        SessionType.Focus -> focusTime
                                        SessionType.ShortBreak -> shortBreakTime
                                        SessionType.LongBreak -> longBreakTime
                                    },
                                ),
                                radius = 40.dp,
                                content = timerValue.toTimer(),
                                mainColor = MaterialTheme.colorScheme.primary,
                                counterColor = MaterialTheme.colorScheme.onPrimary,
                            )
                        }
                    }

                    item {
                        Spacer(modifier = Modifier.height(48.dp))
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = when (task.current.sessionType()) {
                                SessionType.Focus -> "Focus Time"
                                SessionType.ShortBreak -> "Short Break"
                                SessionType.LongBreak -> "Long Break"
                            },
                            style = MaterialTheme.typography.displaySmall.copy(
                                color = MaterialTheme.colorScheme.onBackground,
                            ),
                            textAlign = TextAlign.Center,
                        )
                    }

                    item {
                        Spacer(modifier = Modifier.height(56.dp))
                        BloomTimerControls(
                            modifier = Modifier.fillMaxWidth(),
                            state = timerState,
                            onClickReset = onClickReset,
                            onClickNext = onClickNext,
                            onClickAction = onClickAction,
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalTime::class)
@Preview(showBackground = true)
@Composable
fun FocusTimeScreenContentPreview() {
    val snackbarHostState = remember { SnackbarHostState() }
    val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

    // Fake Task con tu modelo completo
    val mockTask = Task(
        id = 1,
        name = "Estudiar Kotlin Multiplatform",
        description = "Estudiar KMP y crear un proyecto real.",
        type = "Focus",
        start = now,
        date = now,
        color = 0xFF4CAF50, // verde por ejemplo
        current = "0",      // tu campo actual es String (ej: "0", "1200", etc.)
        focusSessions = 4,
        currentCycle = 1,
        completed = false,
        consumedFocusTime = 0L,
        consumedShortBreakTime = 0L,
        consumedLongBreakTime = 0L,
        inProgressTask = true,
        active = true
    )

    MaterialTheme {
        FocusTimeScreenContent(
            focusTime = 25L * 60L * 1000L,       // 25 minutos
            shortBreakTime = 5L * 60L * 1000L,   // 5 minutos
            longBreakTime = 15L * 60L * 1000L,   // 15 minutos
            containerColor = MaterialTheme.colorScheme.background,
            timerValue = 10L * 1000L,            // 10 segundos
            timerState = TimerState.Idle,
            task = mockTask,
            snackbarHostState = snackbarHostState,
            onClickNavigateBack = {},
            onClickAction = {},
            onClickNext = {},
            onClickReset = {},
        )
    }
}
