package org.dionisio.task.app.feature.settings.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.dionisio.task.app.data.presentation.component.BloomTopAppBar
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreenContent(
    optionsOpened: List<String>,
    openOptions: (String) -> Unit,
    focusSessionMinutes: Int,
    onFocusSessionMinutesChange: (String) -> Unit,
    shortBreakMinutes: Int,
    onShortBreakMinutesChange: (String) -> Unit,
    longBreakMinutes: Int,
    onLongBreakMinutesChange: (String) -> Unit,
    hourFormats: List<String>,
    selectedHourFormat: Int,
    onHourFormatChange: (Int) -> Unit,
    showColorDialog: Boolean,
    selectedColorCardTitle: String,
    onColorCardTitleChange: (String) -> Unit,
    onShowColorDialog: (Boolean) -> Unit,
    darkTheme: Boolean,
    onDarkThemeChange: (Boolean) -> Unit,
    currentShortBreakColor: Long,
    currentLongBreakColor: Long,
    currentSessionColor: Long,
    onSelectColor: (String, Long) -> Unit,
    remindersOn: Boolean,
    onRemindersChange: (Boolean) -> Unit,
) {
    Scaffold(
        topBar = {
            BloomTopAppBar(
                hasBackNavigation = false,
            ) {
                Text(text = "Settings")
            }
        },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            item {
                FocusSessionsSetting(
                    focusSessionMinutes = focusSessionMinutes,
                    onFocusSessionMinutesChange = onFocusSessionMinutesChange,
                    shortBreakMinutes = shortBreakMinutes,
                    onShortBreakMinutesChange = onShortBreakMinutesChange,
                    longBreakMinutes = longBreakMinutes,
                    onLongBreakMinutesChange = onLongBreakMinutesChange,
                    expanded = { title ->
                        optionsOpened.contains(title)
                    },
                    onExpand = { title ->
                        openOptions(title)
                    },
                )
            }
            item {
                TimeSetting(
                    expanded = { title ->
                        optionsOpened.contains(title)
                    },
                    onExpand = { title ->
                        openOptions(title)
                    },
                    hourFormats = hourFormats,
                    selectedHourFormat = selectedHourFormat,
                    onHourFormatChange = onHourFormatChange,
                )
            }
            /*item {
                SoundSetting(
                    expanded = { title ->
                        optionsOpened.contains(title)
                    },
                    onExpand = { title ->
                        openOptions(title)
                    }
                )
            }*/
            item {
                ThemeSetting(
                    expanded = { title ->
                        optionsOpened.contains(title)
                    },
                    onExpand = { title ->
                        openOptions(title)
                    },
                    showColorDialog = showColorDialog,
                    selectedColorCardTitle = selectedColorCardTitle,
                    onColorCardTitleChange = onColorCardTitleChange,
                    onShowColorDialog = onShowColorDialog,
                    darkTheme = darkTheme,
                    onDarkThemeChange = onDarkThemeChange,
                    currentShortBreakColor = currentShortBreakColor,
                    currentLongBreakColor = currentLongBreakColor,
                    currentSessionColor = currentSessionColor,
                    onSelectColor = onSelectColor,
                )
            }
            item {
                NotificationsSetting(
                    expanded = { title ->
                        optionsOpened.contains(title)
                    },
                    onExpand = { title ->
                        openOptions(title)
                    },
                    remindersOn = remindersOn,
                    onRemindersChange = onRemindersChange,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenContentPreview() {
    SettingsScreenContent(
        optionsOpened = listOf(),
        openOptions = {},

        focusSessionMinutes = 25,
        onFocusSessionMinutesChange = {},

        shortBreakMinutes = 5,
        onShortBreakMinutesChange = {},

        longBreakMinutes = 15,
        onLongBreakMinutesChange = {},

        hourFormats = listOf("12-hour", "24-hour"),
        selectedHourFormat = 0,
        onHourFormatChange = {},

        showColorDialog = false,
        selectedColorCardTitle = "Focus Session",
        onColorCardTitleChange = {},

        onShowColorDialog = {},

        darkTheme = false,
        onDarkThemeChange = {},

        currentShortBreakColor = 0xFFE57373, // rojo suave
        currentLongBreakColor = 0xFF64B5F6,  // azul suave
        currentSessionColor = 0xFF81C784,    // verde suave

        onSelectColor = { _, _ -> },

        remindersOn = true,
        onRemindersChange = {},
    )
}


