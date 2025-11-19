package org.dionisio.task.app.feature.taskprogress

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.navigation.NavController
import org.dionisio.task.app.core.utils.platform.StatusBarColors
import org.dionisio.task.app.data.domain.model.Task
import org.dionisio.task.app.data.presentation.component.BloomTimerControls
import org.dionisio.task.app.data.presentation.component.BloomTopAppBar
import org.dionisio.task.app.data.presentation.component.TaskProgress
import org.dionisio.task.app.data.presentation.theme.LongBreakColor
import org.dionisio.task.app.data.presentation.theme.SessionColor
import org.dionisio.task.app.data.presentation.theme.ShortBreakColor
import org.dionisio.task.app.data.utils.SessionType
import org.dionisio.task.app.feature.taskprogress.components.FocusTimeScreenContent
import org.dionisio.task.app.feature.taskprogress.components.SuccessfulCompletionOfTask
import org.dionisio.task.app.utils.durationInMinutes
import org.dionisio.task.app.utils.koinViewModel
import org.dionisio.task.app.utils.sessionType
import org.dionisio.task.app.utils.toMinutes
import org.dionisio.task.app.utils.toPercentage
import org.dionisio.task.app.utils.toTimer

@Composable
fun TaskProgressScreenRoot(
    taskId: Int,
    navController: NavController,
    viewModel: TaskProgressViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(taskId) {
        viewModel.onAction(TaskProgressAction.LoadTask(taskId))
    }

    TaskProgressScreen(
        state = state,
        navController = navController,
        onAction = viewModel::onAction

    )
}

@Composable
fun TaskProgressScreen(
    state: TaskProgressState,
    navController: NavController,
    onAction: (TaskProgressAction) -> Unit,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    val task = state.task

    val containerColor = when (task?.current?.sessionType()) {
        SessionType.Focus -> {
            if (state.focusColor == null || state.focusColor == 0L)
                Color(SessionColor)
            else Color(state.focusColor)
        }

        SessionType.LongBreak -> {
            if (state.longBreakColor == null || state.longBreakColor == 0L)
                Color(LongBreakColor)
            else Color(state.longBreakColor)
        }

        SessionType.ShortBreak -> {
            if (state.shortBreakColor == null || state.shortBreakColor == 0L)
                Color(ShortBreakColor)
            else Color(state.shortBreakColor)
        }

        else -> Color(SessionColor)
    }

    StatusBarColors(
        statusBarColor = containerColor,
        navBarColor = containerColor,
    )

    if (task?.completed == true) {
        SuccessfulCompletionOfTask(
            title = "Task Completed",
            message = "You have successfully completed this task",
            onConfirm = {
                Timer.reset()
                navController.popBackStack()
            },
        )
    }

    FocusTimeScreenContent(
        task = task,
        focusTime = state.sessionTime,
        shortBreakTime = state.shortBreakTime,
        longBreakTime = state.longBreakTime,
        timerValue = state.timer,
        snackbarHostState = snackbarHostState,
        timerState = state.timerState,
        containerColor = containerColor,
        onClickNavigateBack = {
            navController.popBackStack()
        },
        onClickNext = { onAction(TaskProgressAction.MoveToNextSession) },
        onClickReset = { onAction(TaskProgressAction.ResetSession) },
        onClickAction = { timerState ->
            onAction(TaskProgressAction.TimerButtonClicked(timerState))
        }
    )
}


