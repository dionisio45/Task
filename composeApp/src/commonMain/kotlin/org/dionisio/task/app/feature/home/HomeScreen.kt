package org.dionisio.task.app.feature.home

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.dionisio.task.app.core.utils.platform.StatusBarColors
import org.dionisio.task.app.data.domain.model.Task
import org.dionisio.task.app.data.presentation.navigation.Destinations
import org.dionisio.task.app.feature.home.component.HomeScreenContent
import org.dionisio.task.app.feature.home.component.TaskOptionsBottomSheet
import org.dionisio.task.app.feature.taskprogress.Timer
import org.dionisio.task.app.feature.taskprogress.TimerState
import org.dionisio.task.app.utils.koinViewModel
import org.dionisio.task.app.utils.today
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@Composable
fun HomeScreenRoot(
    viewModel: HomeViewModel = koinViewModel(),
    navController: NavHostController,
){
    val state by viewModel.uiState.collectAsState()

    // Efectos que dependen del estado
    /*LaunchedEffect(state.remindersOn) {
        if (state.remindersOn == null) {
            viewModel.onAction(HomeAction.OnToggleReminder(1))
        }
    }*/

    LaunchedEffect(Unit) {
        if (state.remindersOn is ReminderState.Loading) {
            viewModel.onAction(HomeAction.OnToggleReminder(1))
        }
    }


    HomeScreen(
        state = state,
        onAction = viewModel::onAction,
        navController = navController
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    state: HomeState,
    onAction: (HomeAction) -> Unit
) {
    StatusBarColors(
        statusBarColor = MaterialTheme.colorScheme.background,
        navBarColor = MaterialTheme.colorScheme.background,
    )

    val timerState = Timer.timerState.collectAsState().value
    val tickingTime = Timer.tickingTime.collectAsState().value

    val bottomSheetState = rememberModalBottomSheetState()
    if (state.isBottomSheetOpen && state.selectedTask != null) {
        TaskOptionsBottomSheet(
            type = if (state.selectedTask.date.date < today().date) "overdue" else "today",
            bottomSheetState = bottomSheetState,
            onClickCancel = {
                onAction(HomeAction.OnOpenBottomSheet(false))
            },
            onClickDelete = {
                onAction(HomeAction.OnDeleteSelectedTask(it))
            },
            onDismissRequest = {
                onAction(HomeAction.OnOpenBottomSheet(false))
                onAction(HomeAction.OnSelectTask(null))
            },
            onClickPushToTomorrow = {
                onAction(HomeAction.OnPushTaskToTomorrow(it))
            },
            onClickPushToToday = {
                onAction(HomeAction.OnPushTaskToToday(it))
            },
            onClickMarkAsCompleted = {
                onAction(HomeAction.OnMarkTaskAsCompleted(it))
            },
            onClickEditTask = {
                navController.navigate(Destinations.AddTask(taskId = state.selectedTask.id ?: 0))
            },
            task = state.selectedTask,
        )
    }

    HomeScreenContent(
        tasksState =  state.tasks,
        timerState =  timerState,
        tickingTime = tickingTime,
        focusTimeColor = state.focusColor,
        shortBreakColor = state.shortBreakColor,
        longBreakColor = state.longBreakColor,
        hourFormat = state.hourFormat,
        sessionTime = state.sessionTime,
        shortBreakTime = state.shortBreakTime,
        longBreakTime = state.longBreakTime,
        username = state.username,
        onClickTask = {
            if (it.date.date < today().date) {
                onAction(HomeAction.OnSelectTask(it))
                onAction(HomeAction.OnOpenBottomSheet(true))
            } else {
                navController.navigate(Destinations.TaskProgress(taskId = it.id!!))
            }
        },
        onClickSeeAllTasks = {
            navController.navigate(Destinations.AllTasks(it))
        },
        onClickTaskOptions = {
            onAction(HomeAction.OnSelectTask(it))
            onAction(HomeAction.OnOpenBottomSheet(true))
        },
        onClickActiveTaskOptions = {
            when (timerState) {
                TimerState.Ticking -> {
                    Timer.pause()
                }

                TimerState.Paused -> {
                    Timer.resume()
                }

                else -> {}
            }
        },
        onClickAddTask = {
            navController.navigate(Destinations.AddTask())
        }
    )
}

@OptIn(ExperimentalTime::class)
@Preview(showBackground = true, name = "HomeScreen Preview")
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()

    HomeScreen(
        navController = navController,
        state = HomeState(
            username = "Dionisio",
            sessionTime = 25,
            shortBreakTime = 5,
            longBreakTime = 15,
            hourFormat = 24,
            focusColor = 0xFF00BCD4,      // Azul
            shortBreakColor = 0xFF4CAF50,  // Verde
            longBreakColor = 0xFFFF9800,   // Naranja
            remindersOn = ReminderState.Success(reminderOn = 1),
            tasks = TasksState.Success(
                tasks = listOf(
                    Task(
                        id = 1,
                        name = "Revisar informes",
                        description = "Completar reporte semanal",
                        type = "work",
                        start = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
                        date = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
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
                        start = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
                        date = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
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
                        start = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
                        date = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
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
        ),
        onAction = {}
    )
}




