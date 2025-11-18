package org.dionisio.task.app.data.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.dionisio.task.app.data.domain.model.Task
import org.dionisio.task.app.utils.calculateEndTime
import org.dionisio.task.app.utils.durationInMinutes
import org.dionisio.task.app.utils.prettyFormat
import org.dionisio.task.app.utils.prettyTimeDifference
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import task.composeapp.generated.resources.Res
import task.composeapp.generated.resources.ic_complete
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalResourceApi::class)
@Composable
fun TaskCard(
    task: Task,
    focusSessions: Int,
    sessionTime: Int,
    shortBreakTime: Int,
    longBreakTime: Int,
    hourFormat: Int,
    onClick: (Task) -> Unit,
    onShowTaskOption: (Task) -> Unit,
    type: String,
) {
    val end by remember {
        mutableStateOf(
            task.start.calculateEndTime(
                focusSessions = focusSessions,
                sessionTime = sessionTime,
                shortBreakTime = shortBreakTime,
                longBreakTime = longBreakTime,
            ),
        )
    }
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        onClick = {
            onClick(task)
        },
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(.85f),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    Text(
                        text = task.name,
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp,
                        ),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                    if (task.description != null) {
                        Text(
                            text = task.description,
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                }
                Icon(
                    modifier = Modifier
                        .clickable {
                            onShowTaskOption(task)
                        },
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = "Task Options",
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column {
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
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${
                        task.durationInMinutes(
                            focusSessions = focusSessions,
                            sessionTime = sessionTime!!.toInt() ,
                            shortBreakTime = shortBreakTime!!.toInt() ,
                            longBreakTime = longBreakTime!!.toInt() ,
                        )
                        } minutes",
                        style = MaterialTheme.typography.bodySmall,
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = prettyTimeDifference(
                            start = task.start,
                            end = end,
                            timeFormat = hourFormat,
                        ),
                        style = MaterialTheme.typography.bodySmall,
                    )
                    if (type == "overdue") {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = task.date.date.prettyFormat(),
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontWeight = FontWeight.SemiBold,
                            ),
                        )
                    }
                }
                if (task.completed) {
                    Image(
                        modifier = Modifier
                            .size(48.dp),
                        painter = painterResource(
                            Res.drawable.ic_complete),
                        contentDescription = "Task Options",
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primary),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            imageVector = Icons.Filled.PlayArrow,
                            contentDescription = "Task Options",
                            tint = MaterialTheme.colorScheme.onPrimary,
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalTime::class)
val now: LocalDateTime = kotlin.time.Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

@Preview(showBackground = true)
@Composable
fun TaskCardPreview() {
    val exampleTask = Task(
        id = 1,
        name = "Estudiar Jetpack Compose",
        description = "Leer la documentación y practicar componibles",
        type = "normal",
        start = now,
        color = 0xFF6200EE,
        current = "0",
        date = now,
        focusSessions = 4,
        currentCycle = 1,
        completed = false,
        consumedFocusTime = 0L,
        consumedShortBreakTime = 0L,
        consumedLongBreakTime = 0L,
        inProgressTask = false,
        active = true,
    )

    MaterialTheme {
        TaskCard(
            task = exampleTask,
            focusSessions = 4,
            sessionTime = 25,
            shortBreakTime = 5,
            longBreakTime = 15,
            hourFormat = 24,
            onClick = {},
            onShowTaskOption = {},
            type = exampleTask.type
        )
    }
}


