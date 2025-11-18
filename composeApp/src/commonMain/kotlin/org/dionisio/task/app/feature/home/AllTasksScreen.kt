package org.dionisio.task.app.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import kotlinx.datetime.LocalDateTime
import org.dionisio.task.app.core.utils.platform.StatusBarColors
import org.dionisio.task.app.data.domain.model.Task
import org.dionisio.task.app.data.presentation.component.BloomTopAppBar
import org.dionisio.task.app.data.presentation.component.TaskCard
import org.dionisio.task.app.data.presentation.navigation.Destinations
import org.dionisio.task.app.feature.home.component.TaskOptionsBottomSheet
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AllTasksScreenRoot(
    type: String,
    viewModel: HomeViewModel = koinViewModel(),
    navController: NavHostController,
) {
    val state by viewModel.uiState.collectAsState()

    AllTasksScreen(
        type = type,
        navController = navController,
        state = state,
        onAction = viewModel::onAction,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllTasksScreen(
    type: String,
    navController: NavController,
    state: HomeState,
    onAction: (HomeAction) -> Unit
) {
    StatusBarColors(
        statusBarColor = MaterialTheme.colorScheme.background,
        navBarColor = MaterialTheme.colorScheme.background,
    )

    val bottomSheetState = rememberModalBottomSheetState()
    if (state.isBottomSheetOpen && state.selectedTask != null) {
        TaskOptionsBottomSheet(
            type = type,
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
                navController.navigate(Destinations.AddTask(taskId = it.id!!))
            },
            task = state.selectedTask,
        )
    }

    AllTasksScreenContent(
        type = type,
        tasksState = state.tasks,
        timeFormat = state.hourFormat,
        sessionTime = state.sessionTime,
        shortBreakTime = state.shortBreakTime,
        longBreakTime = state.longBreakTime,
        onClickNavigateBack = {
            navController.popBackStack()
        },
        onClickTaskOptions = {
            onAction(HomeAction.OnSelectTask(it))
            onAction(HomeAction.OnOpenBottomSheet(true))
        },
        onClickTask = {
            navController.navigate(Destinations.TaskProgress(taskId = it.id!!))
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllTasksScreenContent(
    tasksState: TasksState,
    timeFormat: Int,
    sessionTime: Int,
    shortBreakTime: Int,
    longBreakTime: Int,
    onClickNavigateBack: () -> Unit,
    onClickTaskOptions: (task: Task) -> Unit,
    onClickTask: (task: Task) -> Unit,
    type: String,
) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        when (tasksState) {
            is TasksState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                )
            }

            is TasksState.Success -> {
                val tasks = tasksState.tasks
                val overdueTasks = tasksState.overdueTasks
                Scaffold(
                    topBar = {
                        BloomTopAppBar(
                            hasBackNavigation = true,
                            navigationIcon = {
                                IconButton(onClick = onClickNavigateBack) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                                        contentDescription = "Back",
                                    )
                                }
                            },
                        ) {
                            Text(
                                text = "${
                                    if (type == "today") "Today's" else "Overdue"
                                } Tasks (${
                                    if (type == "today") tasks.size else overdueTasks.size
                                })",
                                color = if (type == "today") MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.error,
                            )
                        }
                    },
                ) { paddingValues ->
                    LazyColumn(
                        modifier = Modifier
                            .padding(paddingValues)
                            .fillMaxSize(),
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        items(
                            items = if (type == "today") tasks else overdueTasks,
                            key = { it.id!! },
                        ) {
                            TaskCard(
                                type = type,
                                task = it,
                                hourFormat = timeFormat,
                                onClick = onClickTask,
                                onShowTaskOption = onClickTaskOptions,
                                focusSessions = it.focusSessions,
                                sessionTime = sessionTime,
                                shortBreakTime = shortBreakTime,
                                longBreakTime = longBreakTime,
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun AllTasksScreenPreview() {
    val dummyTasks = listOf(
        Task(
            id = 1,
            name = "Design new UI",
            description = "Refactor the home screen layout",
            type = "today",
            start = LocalDateTime(2025, 11, 6, 9, 0),
            date = LocalDateTime(2025, 11, 6, 12, 0),
            color = 0xFF64B5F6,
            current = "Focus",
            focusSessions = 2,
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
            name = "Update API models",
            description = "Adjust JSON mappings",
            type = "today",
            start = LocalDateTime(2025, 11, 6, 13, 0),
            date = LocalDateTime(2025, 11, 6, 15, 0),
            color = 0xFFFFA726,
            current = "Focus",
            focusSessions = 1,
            currentCycle = 0,
            completed = true,
            consumedFocusTime = 1200L,
            consumedShortBreakTime = 300L,
            consumedLongBreakTime = 0L,
            inProgressTask = false,
            active = true
        )
    )

    val tasksState = TasksState.Success(
        tasks = dummyTasks,
        overdueTasks = emptyList()
    )

    AllTasksScreenContent(
        tasksState = tasksState,
        timeFormat = 24,
        sessionTime = 25,
        shortBreakTime = 5,
        longBreakTime = 15,
        onClickNavigateBack = {},
        onClickTaskOptions = {},
        onClickTask = {},
        type = "today"
    )
}

