package org.dionisio.task.app.feature.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.dionisio.task.app.data.utils.SessionType
import org.dionisio.task.app.feature.taskprogress.TimerState
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.time.ExperimentalTime
import org.dionisio.task.app.data.domain.model.Task

@Composable
fun ActiveTaskCard(
    task: Task,
    timerState: TimerState,
    tickingTime: Long,
    onClick: (Task) -> Unit,
    onClickTaskOptions: (Task) -> Unit,
    containerColor: Color,
) {
    Card(
        onClick = {
            onClick(task)
        },
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
        ),
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(.85f),
                verticalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                Text(
                    text = task.name,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onPrimary,
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = "${
                        when (task.current.sessionType()) {
                            SessionType.Focus -> {
                                "Focus Session"
                            }

                            SessionType.ShortBreak -> {
                                "Short Break"
                            }

                            SessionType.LongBreak -> {
                                "Long Break"
                            }
                        }
                    } - ${
                        tickingTime.toTimer()
                    }",
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.SemiBold,
                    ),
                )
            }
            IconButton(
                onClick = {
                    onClickTaskOptions(task)
                },
            ) {
                Icon(
                    modifier = Modifier,
                    imageVector = when (timerState) {
                        TimerState.Ticking -> {
                            Icons.Default.Pause
                        }

                        TimerState.Paused -> {
                            Icons.Default.PlayArrow
                        }

                        else -> {
                            Icons.Default.PlayArrow
                        }
                    },
                    contentDescription = "Play/Pause",
                    tint = MaterialTheme.colorScheme.onPrimary,
                )
            }
        }
    }
}

enum class TimerState1 {
    Ticking,
    Paused,
    Stopped
}

// SessionType de ejemplo
enum class SessionType {
    Focus, ShortBreak, LongBreak
}

// Función de extensión para SessionType simulada
fun String.sessionType(): SessionType {
    return SessionType.Focus
}

// Función para formatear segundos a mm:ss
fun Long.toTimer(): String {
    val minutes = this / 60
    val seconds = this % 60
    return minutes.toString().padStart(2, '0') + ":" + seconds.toString().padStart(2, '0')
}

// Data class Task simulada
data class Task1(
    val id: Int = 0,
    val name: String,
    val current: String,
)

// Preview
@OptIn(ExperimentalTime::class)
@Preview(showBackground = true)
@Composable
fun ActiveTaskCardPreview() {
    val now: LocalDateTime = kotlin.time.Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    val sampleTask = Task(
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
    )

    MaterialTheme {
        ActiveTaskCard(
            task = sampleTask,
            timerState = TimerState.Ticking,
            tickingTime = 5 * 60L, // 5 minutos
            onClick = {},
            onClickTaskOptions = {},
            containerColor = MaterialTheme.colorScheme.primary
        )
    }
}