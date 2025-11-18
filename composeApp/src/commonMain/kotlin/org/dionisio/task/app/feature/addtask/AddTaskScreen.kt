package org.dionisio.task.app.feature.addtask

/*import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import org.dionisio.task.app.core.utils.platform.StatusBarColors
import org.dionisio.task.app.data.utils.taskTypes
import org.dionisio.task.app.feature.addtask.components.AddTaskScreenContent
import org.dionisio.task.app.feature.addtask.components.TaskDatePicker
import org.dionisio.task.app.feature.addtask.components.TimerInputDialog
import org.dionisio.task.app.utils.UiEvents
import org.dionisio.task.app.utils.calculateFromFocusSessions
import org.dionisio.task.app.utils.today
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import org.dionisio.task.app.data.domain.model.Task
import org.dionisio.task.app.utils.toLocalDateTime*/

/*@Composable
fun AddTaskScreenRoot(
    taskId: Int?,
    navController: NavHostController,
    viewModel: AddTaskViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState()

    AddTaskScreen(
        taskId = taskId,
        state = state,
        navController = navController,
        onAction = viewModel::onAction,
        events = viewModel.events,
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
@Composable
fun AddTaskScreen(
    taskId: Int? = null,
    state: AddTaskState,
    navController: NavController,
    onAction: (AddTaskAction) -> Unit,
    events: Flow<UiEvents>,
) {
    StatusBarColors(
        statusBarColor = MaterialTheme.colorScheme.background,
        navBarColor = MaterialTheme.colorScheme.background,
    )
    val snackbarHostState = remember { SnackbarHostState() }
    val keyboardController = LocalSoftwareKeyboardController.current

    val startTimeState = rememberTimePickerState(
        initialHour = today().hour,
        initialMinute = today().minute,
        is24Hour = state.hourFormat == 24,
    )

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Clock.System.now().toEpochMilliseconds(),
    )

    LaunchedEffect(key1 = true) {
        onAction(AddTaskAction.OnLoadTask(taskId))

        onAction(
            AddTaskAction.OnSetEndTime(
                calculateFromFocusSessions(
                    focusSessions = state.focusSessions,
                    sessionTime = state.sessionTime,
                    shortBreakTime = state.shortBreakTime,
                    longBreakTime = state.longBreakTime,
                    currentLocalDateTime = LocalDateTime(
                        year = state.taskDate.year,
                        month = state.taskDate.month,
                        day = state.taskDate.day,
                        hour = state.startTime.hour,
                        minute = state.startTime.minute,
                    ),
                )
            )
        )
    }

    LaunchedEffect(Unit) {
        events.collect { event ->
            when (event) {
                is UiEvents.ShowSnackbar -> snackbarHostState.showSnackbar(event.message)
                UiEvents.NavigateBack -> {
                    navController.popBackStack()
                }

                else -> {}
            }
        }
    }

    if (state.showStartTimeInputDialog) {
        TimerInputDialog(
            title = "Start Time",
            state = startTimeState,
            onDismiss = {
                onAction(AddTaskAction.OnShowStartTimeInputDialog(false))
            },
            onConfirmStartTime = {
                onAction(AddTaskAction.OnSetStartTime(it))
                onAction(
                    AddTaskAction.OnSetEndTime(
                        calculateFromFocusSessions(
                            focusSessions = state.focusSessions,
                            sessionTime = state.sessionTime,
                            shortBreakTime = state.shortBreakTime,
                            longBreakTime = state.longBreakTime,
                            currentLocalDateTime = LocalDateTime(
                                year = state.taskDate.year,
                                month = state.taskDate.month,
                                day = state.taskDate.day,
                                hour = it.hour,
                                minute = it.minute,
                            ),
                        ),
                    )
                )
                onAction(AddTaskAction.OnShowStartTimeInputDialog(false))
            },
        )
    }

    if (state.showTaskDatePickerDialog) {
        TaskDatePicker(
            datePickerState = datePickerState,
            dismiss = {
                onAction(AddTaskAction.OnTaskShowDatePickerDialog(false))
            },
            onConfirmDate = {
                onAction(AddTaskAction.OnSetDate(it))
                onAction(AddTaskAction.OnTaskShowDatePickerDialog(false))
            },
        )
    }

    AddTaskScreenContent(
        snackbarHostState = snackbarHostState,
        hourFormat = state.hourFormat,
        taskOptions = taskTypes,
        selectedTaskType = state.type,
        taskName = state.name,
        taskDescription = state.description,
        taskDate = state.taskDate,
        startTime = state.startTime,
        endTime = state.endTime,
        focusSessions = state.focusSessions,
        onTaskNameChange = { onAction(AddTaskAction.OnSetName(it)) },
        onIncrementFocusSessions = { onAction(AddTaskAction.OnIncrementSessions) },
        onDecrementFocusSessions = { onAction(AddTaskAction.OnDecrementSessions) },
        onSelectedTaskTypeChange = { onAction(AddTaskAction.OnSetType(it)) },
        onTaskDescriptionChange = { onAction(AddTaskAction.OnSetDescription(it)) },
        onClickPickStartTime = { onAction(AddTaskAction.OnShowStartTimeInputDialog(true)) },
        onClickPickDate = { onAction(AddTaskAction.OnTaskShowDatePickerDialog(true)) },
        onClickAddTask = {
            keyboardController?.hide()
            if (state.id != null) {
            onAction(
                AddTaskAction.OnAddOrUpdateTask(
                    task = Task(
                        id = state.id,
                        name = state.name,
                        description = state.description,
                        start = toLocalDateTime(
                            date = state.taskDate.date,
                            hour = state.startTime.hour,
                            minute = state.startTime.minute,
                        ),
                        color = state.color,
                        current = "Focus",
                        date = state.taskDate,
                        focusSessions = state.focusSessions,
                        completed = false,
                        type = state.type.name,
                        consumedFocusTime = 0L,
                        consumedShortBreakTime = 0L,
                        consumedLongBreakTime = 0L,
                        inProgressTask = false,
                        currentCycle = 0,
                        active = false,
                    ),
                )
            )
        } else {
            onAction(
                AddTaskAction.OnAddOrUpdateTask(
                    task = Task(
                        name = state.name,
                        description = state.description,
                        start = toLocalDateTime(
                            date = state.taskDate.date,
                            hour = state.startTime.hour,
                            minute = state.startTime.minute,
                        ),
                        color = state.color,
                        current = "Focus",
                        date = state.taskDate,
                        focusSessions = state.focusSessions,
                        completed = false,
                        type = state.type.name,
                        consumedFocusTime = 0L,
                        consumedShortBreakTime = 0L,
                        consumedLongBreakTime = 0L,
                        inProgressTask = false,
                        currentCycle = 0,
                        active = false,
                    )
                )
            )
        }
        },
    )
}

@Preview(showBackground = true)
@Composable
fun AddTaskScreenPreview() {
    val dummyState = AddTaskState(
        name = "Write Report",
        description = "Prepare the project status report for the client",
        focusSessions = 3,
        startTime = LocalTime(hour = 9, minute = 0),
        endTime = LocalTime(hour = 11, minute = 0),
        taskDate = today(),
        color = 0xFF4CAF50,
        type = taskTypes.first(),
    )

    // NavController y Flow simulados
    val fakeNavController = rememberNavController()
    val fakeEvents = remember { MutableSharedFlow<UiEvents>() }

    AddTaskScreen(
        taskId = null,
        state = dummyState,
        navController = fakeNavController,
        onAction = {},
        events = fakeEvents
    )
}*/

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import org.dionisio.task.app.core.utils.platform.StatusBarColors
import org.dionisio.task.app.data.domain.model.Task
import org.dionisio.task.app.data.utils.taskTypes
import org.dionisio.task.app.feature.addtask.components.AddTaskScreenContent
import org.dionisio.task.app.feature.addtask.components.TaskDatePicker
import org.dionisio.task.app.feature.addtask.components.TimerInputDialog
import org.dionisio.task.app.utils.*
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import org.dionisio.task.app.utils.UiEvents

@Composable
fun AddTaskScreenRoot(
    taskId: Int?,
    navController: NavController,
    viewModel: AddTaskViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState()

    AddTaskScreen(
        taskId = taskId,
        state = state,
        navController = navController,
        onAction = viewModel::onAction,
        events = viewModel.events,
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalTime::class)
@Composable
fun AddTaskScreen(
    taskId: Int? = null,
    state: AddTaskState,
    navController: NavController,
    onAction: (AddTaskAction) -> Unit,
    events: Flow<UiEvents>,
) {
    StatusBarColors(
        statusBarColor = MaterialTheme.colorScheme.background,
        navBarColor = MaterialTheme.colorScheme.background,
    )

    val snackbarHostState = remember { SnackbarHostState() }
    val keyboardController = LocalSoftwareKeyboardController.current

    val startTimeState = rememberTimePickerState(
        initialHour = today().hour,
        initialMinute = today().minute,
        is24Hour = state.hourFormat == 24,
    )

    val datePickerState = rememberDatePickerState(
        initialSelectedDateMillis = Clock.System.now().toEpochMilliseconds(),
    )

    // --- Efectos iniciales ---
    HandleInitialLoad(taskId, state, onAction)
    HandleUiEvents(events, snackbarHostState, navController)

    // --- Diálogos dinámicos ---
    if (state.showStartTimeInputDialog) {
        TimerInputDialog(
            title = "Start Time",
            state = startTimeState,
            onDismiss = { onAction(AddTaskAction.OnShowStartTimeInputDialog(false)) },
            onConfirmStartTime = { selectedTime ->
                onAction(AddTaskAction.OnSetStartTime(selectedTime))
                onAction(AddTaskAction.OnSetEndTime(
                    calculateFromFocusSessions(
                        focusSessions = state.focusSessions,
                        sessionTime = state.sessionTime,
                        shortBreakTime = state.shortBreakTime,
                        longBreakTime = state.longBreakTime,
                        currentLocalDateTime = LocalDateTime(
                            state.taskDate.year,
                            state.taskDate.month,
                            state.taskDate.day,
                            selectedTime.hour,
                            selectedTime.minute,
                        )
                    )
                ))
                onAction(AddTaskAction.OnShowStartTimeInputDialog(false))
            },
        )
    }

    if (state.showTaskDatePickerDialog) {
        TaskDatePicker(
            datePickerState = datePickerState,
            dismiss = { onAction(AddTaskAction.OnTaskShowDatePickerDialog(false)) },
            onConfirmDate = {
                onAction(AddTaskAction.OnSetDate(it))
                onAction(AddTaskAction.OnTaskShowDatePickerDialog(false))
            },
        )
    }

    // --- Contenido principal ---
    AddTaskScreenContent(
        snackbarHostState = snackbarHostState,
        hourFormat = state.hourFormat,
        taskOptions = taskTypes,
        selectedTaskType = state.type,
        taskName = state.name,
        taskDescription = state.description,
        taskDate = state.taskDate,
        startTime = state.startTime,
        endTime = state.endTime,
        focusSessions = state.focusSessions,
        onTaskNameChange = { onAction(AddTaskAction.OnSetName(it)) },
        onTaskDescriptionChange = { onAction(AddTaskAction.OnSetDescription(it)) },
        onIncrementFocusSessions = { onAction(AddTaskAction.OnIncrementSessions) },
        onDecrementFocusSessions = { onAction(AddTaskAction.OnDecrementSessions) },
        onSelectedTaskTypeChange = { onAction(AddTaskAction.OnSetType(it)) },
        onClickPickStartTime = { onAction(AddTaskAction.OnShowStartTimeInputDialog(true)) },
        onClickPickDate = { onAction(AddTaskAction.OnTaskShowDatePickerDialog(true)) },
        onClickAddTask = {
            keyboardController?.hide()
            onAction(AddTaskAction.OnAddOrUpdateTask(buildTaskFromState(state)))
        },
    )
}

/**
 * Maneja la carga inicial del task (si existe) y el cálculo inicial de tiempo final.
 */
@OptIn(ExperimentalTime::class)
@Composable
private fun HandleInitialLoad(
    taskId: Int?,
    state: AddTaskState,
    onAction: (AddTaskAction) -> Unit
) {
    LaunchedEffect(Unit) {
        onAction(AddTaskAction.OnLoadTask(taskId))
        onAction(AddTaskAction.OnSetEndTime(
            calculateFromFocusSessions(
                focusSessions = state.focusSessions,
                sessionTime = state.sessionTime,
                shortBreakTime = state.shortBreakTime,
                longBreakTime = state.longBreakTime,
                currentLocalDateTime = LocalDateTime(
                    state.taskDate.year,
                    state.taskDate.month,
                    state.taskDate.day,
                    state.startTime.hour,
                    state.startTime.minute,
                ),
            )
        ))
    }
}

/**
 * Escucha los eventos de UI (snackbar, navegación, etc.)
 */
@Composable
private fun HandleUiEvents(
    events: Flow<UiEvents>,
    snackbarHostState: SnackbarHostState,
    navController: NavController
) {
    LaunchedEffect(Unit) {
        events.collect { event ->
            when (event) {
                is UiEvents.ShowSnackbar -> snackbarHostState.showSnackbar(event.message)
                UiEvents.NavigateBack -> navController.popBackStack()
                else -> Unit
            }
        }
    }
}

/**
 * Construye un objeto Task desde el estado actual.
 */
private fun buildTaskFromState(state: AddTaskState): Task = Task(
    id = state.id,
    name = state.name,
    description = state.description,
    start = toLocalDateTime(
        date = state.taskDate.date,
        hour = state.startTime.hour,
        minute = state.startTime.minute,
    ),
    color = state.color,
    current = "Focus",
    date = state.taskDate,
    focusSessions = state.focusSessions,
    completed = false,
    type = state.type.name,
    consumedFocusTime = 0L,
    consumedShortBreakTime = 0L,
    consumedLongBreakTime = 0L,
    inProgressTask = false,
    currentCycle = 0,
    active = false,
)

@Preview(showBackground = true)
@Composable
fun AddTaskScreenPreview() {
    val dummyState = AddTaskState(
        name = "Write Report",
        description = "Prepare the project status report for the client",
        focusSessions = 3,
        startTime = LocalTime(hour = 9, minute = 0),
        endTime = LocalTime(hour = 11, minute = 0),
        taskDate = today(),
        color = 0xFF4CAF50,
        type = taskTypes.first(),
    )

    val fakeNavController = rememberNavController()
    val fakeEvents = remember { MutableSharedFlow<UiEvents>() }

    MaterialTheme {
        AddTaskScreen(
            state = dummyState,
            navController = fakeNavController,
            onAction = {},
            events = fakeEvents,
        )
    }
}

