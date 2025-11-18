package org.dionisio.task.app.feature.addtask.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import org.dionisio.task.app.data.presentation.component.BloomButton
import org.dionisio.task.app.data.presentation.component.BloomDateBoxField
import org.dionisio.task.app.data.presentation.component.BloomDropDown
import org.dionisio.task.app.data.presentation.component.BloomIncrementer
import org.dionisio.task.app.data.presentation.component.BloomInputTextField
import org.dionisio.task.app.data.presentation.component.BloomTopAppBar
import org.dionisio.task.app.data.presentation.theme.SuccessColor
import org.dionisio.task.app.data.utils.TaskType
import org.dionisio.task.app.data.utils.TextFieldState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import task.composeapp.generated.resources.Res
import task.composeapp.generated.resources.end_time
import task.composeapp.generated.resources.ic_complete
import task.composeapp.generated.resources.other
import task.composeapp.generated.resources.personal
import task.composeapp.generated.resources.start_time
import task.composeapp.generated.resources.study
import task.composeapp.generated.resources.work

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreenContent(
    snackbarHostState: SnackbarHostState,
    hourFormat: Int,
    taskOptions: List<TaskType>,
    selectedTaskType: TaskType,
    onSelectedTaskTypeChange: (TaskType) -> Unit,
    taskName: String,
    taskDescription: String,
    onTaskDescriptionChange: (String) -> Unit,
    focusSessions: Int,
    onTaskNameChange: (String) -> Unit,
    onIncrementFocusSessions: () -> Unit,
    onDecrementFocusSessions: () -> Unit,
    onClickAddTask: () -> Unit,
    onClickPickStartTime: () -> Unit,
    onClickPickDate: () -> Unit,
    // startTimePickerState: TimePickerState,
    // datePickerState: DatePickerState,
    taskDate: LocalDateTime,
    startTime: LocalTime,
    endTime: LocalTime,
) {
    Scaffold(
        snackbarHost = {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter, // Change to your desired position
            ) {
                SnackbarHost(
                    hostState = snackbarHostState,
                    snackbar = {
                        Card(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .clickable {
                                    snackbarHostState.currentSnackbarData?.dismiss()
                                },
                            border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.secondary,
                            ),
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                            ) {
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth(.85f),
                                    text = it.visuals.message,
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        color = MaterialTheme.colorScheme.onPrimary,
                                    ),
                                )
                                Image(
                                    modifier = Modifier
                                        .size(32.dp),
                                    painter = painterResource(
                                        Res.drawable.ic_complete
                                    ),
                                    contentDescription = "Task Options",
                                )
                            }
                        }
                    },
                )
            }
        },
        topBar = {
            BloomTopAppBar {
                Text(text = "Add Task")
            }
        },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier.padding(paddingValues),
            contentPadding = PaddingValues(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            item {
                BloomInputTextField(
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3,
                    label = {
                        Text(
                            text = "Task Name",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp,
                            ),
                        )
                    },
                    value = TextFieldState(text = taskName),
                    onValueChange = onTaskNameChange,
                    placeholder = {
                        Text(
                            text = "Enter Task Name",
                            style = MaterialTheme.typography.titleSmall,
                        )
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        capitalization = KeyboardCapitalization.Words,
                    ),
                    textStyle = MaterialTheme.typography.titleSmall.copy(
                        fontSize = 16.sp,
                    ),
                )
            }
            item {
                BloomInputTextField(
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 5,
                    label = {
                        Text(
                            text = "Description",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp,
                            ),
                        )
                    },
                    value = TextFieldState(text = taskDescription),
                    onValueChange = onTaskDescriptionChange,
                    placeholder = {
                        Text(
                            text = "Enter Description",
                            style = MaterialTheme.typography.titleSmall,
                        )
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        capitalization = KeyboardCapitalization.Sentences,
                    ),
                    textStyle = MaterialTheme.typography.titleSmall.copy(
                        fontSize = 16.sp,
                    ),
                )
            }
            item {
                BloomDateBoxField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = {
                        Text(
                            text = "Date",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp,
                            ),
                        )
                    },
                    currentTextState = TextFieldState(
                        text = taskDate.date.toString(),
                    ),
                    onClick = onClickPickDate,
                    textStyle = MaterialTheme.typography.titleSmall.copy(
                        fontSize = 16.sp,
                    ),
                )
            }

            item {
                BloomDropDown(
                    label = {
                        Text(
                            text = "Task Type",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp,
                            ),
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    options = taskOptions,
                    selectedOption = TextFieldState(selectedTaskType.name),
                    onOptionSelected = onSelectedTaskTypeChange,
                    textStyle = MaterialTheme.typography.titleSmall.copy(
                        fontSize = 16.sp,
                    ),
                )
            }

            item {
                Spacer(modifier = Modifier.height(12.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    TimeComponent(
                        time = startTime,
                        hourFormat = hourFormat,
                        title = "Start Time",
                        icon = Res.drawable.start_time,
                        iconColor = MaterialTheme.colorScheme.primary,
                        iconSize = 24,
                        onClick = onClickPickStartTime,
                    )

                    DashedDivider(
                        color = MaterialTheme.colorScheme.primary,
                        thickness = 3.dp,
                        phase = 5f,
                        modifier = Modifier
                            .width(180.dp),
                    )

                    TimeComponent(
                        time = endTime,
                        hourFormat = hourFormat,
                        title = "End Time",
                        icon = Res.drawable.end_time,
                        iconColor = SuccessColor,
                        onClick = {},
                    )
                }
            }

            item {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Focus Sessions",
                    style = MaterialTheme.typography.titleMedium.copy(
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                    ),
                )
            }

            item {
                BloomIncrementer(
                    modifier = Modifier.fillMaxWidth(),
                    onClickRemove = {
                        onDecrementFocusSessions()
                    },
                    onClickAdd = {
                        onIncrementFocusSessions()
                    },
                    currentValue = focusSessions,
                )
            }

            item {
                Spacer(modifier = Modifier.height(12.dp))
            }

            item {
                BloomButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    onClick = onClickAddTask,
                    content = {
                        Text(text = "Save")
                    },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddTaskScreenContentPreview() {
    val fakeTaskTypes = listOf(
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

    MaterialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            AddTaskScreenContent(
                snackbarHostState = SnackbarHostState(),
                hourFormat = 24,
                taskOptions = fakeTaskTypes,
                selectedTaskType = fakeTaskTypes.first(),
                onSelectedTaskTypeChange = {},
                taskName = "Read Kotlin documentation",
                taskDescription = "Learn about coroutines and Compose best practices.",
                onTaskDescriptionChange = {},
                focusSessions = 3,
                onTaskNameChange = {},
                onIncrementFocusSessions = {},
                onDecrementFocusSessions = {},
                onClickAddTask = {},
                onClickPickStartTime = {},
                onClickPickDate = {},
                taskDate = LocalDateTime(2025, 11, 7, 0, 0),
                startTime = LocalTime(9, 0),
                endTime = LocalTime(10, 30),
            )
        }
    }
}