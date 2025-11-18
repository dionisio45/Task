package org.dionisio.task.app.feature.settings

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import org.dionisio.task.app.core.utils.platform.StatusBarColors
import org.dionisio.task.app.data.presentation.theme.LongBreakColor
import org.dionisio.task.app.data.presentation.theme.SessionColor
import org.dionisio.task.app.data.presentation.theme.ShortBreakColor
import org.dionisio.task.app.feature.settings.components.SettingsScreenContent
import org.dionisio.task.app.utils.isDigitsOnly
import org.dionisio.task.app.utils.koinViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SettingsScreenRoot(
    navController: NavController,
    viewModel: SettingsViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState()

    SettingsScreen(
        state = state,
        navController = navController,
        onAction = viewModel::onAction
    )
}

@Composable
fun SettingsScreen(
    state: SettingsState,
    navController: NavController,
    onAction: (SettingsAction) -> Unit,
) {
    val darkTheme = when (state.appTheme) {
         1 -> true
        else -> false
    }
    StatusBarColors(
        statusBarColor = MaterialTheme.colorScheme.background,
        navBarColor = MaterialTheme.colorScheme.background,
    )

    SettingsScreenContent(
        darkTheme = darkTheme,
        onDarkThemeChange = { themeValue ->
            onAction(SettingsAction.OnSetAppTheme(if (themeValue) 1 else 0))
        },
        optionsOpened = state.optionsOpened,
        openOptions = { option ->
            onAction(SettingsAction.OnToggleOption(option))
        },
        focusSessionMinutes = state.sessionTime ?: 25,
        onFocusSessionMinutesChange = { time ->
            if (time.isEmpty()) {
                onAction(SettingsAction.OnSetSessionTime(0))
                return@SettingsScreenContent
            }
            if (time.isDigitsOnly().not()) {
                return@SettingsScreenContent
            }
            onAction(SettingsAction.OnSetSessionTime(time.toInt()))
        },
        shortBreakMinutes = state.shortBreakTime ?: 5,
        onShortBreakMinutesChange = { time ->
            if (time.isEmpty()) {
                onAction(SettingsAction.OnSetShortBreakTime(0))
                return@SettingsScreenContent
            }
            if (time.isDigitsOnly().not()) {
                return@SettingsScreenContent
            }
            onAction(SettingsAction.OnSetShortBreakTime(time.toInt()))
        },
        longBreakMinutes = state.longBreakTime ?: 15,
        onLongBreakMinutesChange = { time ->
            if (time.isEmpty()) {
                onAction(SettingsAction.OnSetLongBreakTime(0))
                return@SettingsScreenContent
            }
            if (time.isDigitsOnly().not()) {
                return@SettingsScreenContent
            }
            onAction(SettingsAction.OnSetLongBreakTime(time.toInt()))
 
        },
        hourFormats =  state.hourFormats,
        selectedHourFormat = state.timeFormat ?: 12,
        onHourFormatChange = {
            onAction(SettingsAction.OnSetHourFormat(it))
        },
        showColorDialog =  state.showColorDialog,
        selectedColorCardTitle = state.selectedColorCardTitle,
        onColorCardTitleChange = {
            onAction(SettingsAction.OnSetSelectedColorCardTitle(it))
        },
        onShowColorDialog = {
            onAction(SettingsAction.OnToggleColorDialog)
        },
        currentShortBreakColor = if (state.shortBreakColor?.toInt() == 0 || state.shortBreakColor == null) {
            ShortBreakColor
        } else {
            state.shortBreakColor
        },
        currentLongBreakColor = if (state.longBreakColor?.toInt() == 0 || state.longBreakColor == null) {
            LongBreakColor
        } else {
            state.longBreakColor
        },
        currentSessionColor = if (state.focusColor?.toInt() == 0 || state.focusColor == null) {
            SessionColor
        } else {
            state.focusColor
        },
        onSelectColor = { title, color ->
            when (title) {
                "Focus Session" -> onAction(SettingsAction.OnSetFocusColor(color))
                "Short Break" -> onAction(SettingsAction.OnSetShortBreakColor(color))
                "Long Break" -> onAction(SettingsAction.OnSetLongBreakColor(color))
            }
        },
        remindersOn = state.remindersOn == 1,
        onRemindersChange = {
            onAction(SettingsAction.OnToggleReminder(
         if (it) {
                    1
                } else {
                    0
                },
             )
           )
        },
    )
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    val mockState = SettingsState(
        appTheme = 1,
        sessionTime = 25,
        shortBreakTime = 5,
        longBreakTime = 15,
        timeFormat = 12,
        focusColor = 0xFF2196F3,
        shortBreakColor = 0xFF4CAF50,
        longBreakColor = 0xFFF44336,
        remindersOn = 1,
        optionsOpened = listOf("Timing", "Appearance"),
        showColorDialog = false,
        selectedColorCardTitle = ""
    )

    val navController = rememberNavController()

    SettingsScreen(
        state = mockState,
        navController = navController,
        onAction = {}
    )
}
