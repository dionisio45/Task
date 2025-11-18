package org.dionisio.task.app.feature.settings.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ThemeSetting(
    onExpand: (String) -> Unit,
    expanded: (String) -> Boolean,
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
) {
    SettingCard(
        onExpand = {
            onExpand("Theme")
        },
        expanded = expanded("Theme"),
        title = "Theme",
        icon = Icons.Outlined.LightMode,
        content = {
            if (showColorDialog) {
                ColorsDialog(
                    title = "Choose $selectedColorCardTitle Color",
                    onDismiss = {
                        onShowColorDialog(false)
                    },
                    onSelectColor = {selectedColor ->
                        onShowColorDialog(false)
                        onSelectColor(selectedColorCardTitle, selectedColor)
                    },
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = "Sessions Color Scheme")
                ColorsSelection(
                    onSelectSessionColor = {
                        onShowColorDialog(true)
                        onColorCardTitleChange("Focus Session")
                    },
                    onSelectShortBreakColor = {
                        onShowColorDialog(true)
                        onColorCardTitleChange("Short Break")
                    },
                    onSelectLongBreakColor = {
                        onShowColorDialog(true)
                        onColorCardTitleChange("Long Break")
                    },
                    currentSessionColor = currentSessionColor,
                    currentShortBreakColor = currentShortBreakColor,
                    currentLongBreakColor = currentLongBreakColor,
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            AutoStartSession(
                title = "App Theme (${
                    if (darkTheme) {
                        "Dark"
                    } else {
                        "Light"
                    }
                })",
                checked = darkTheme,
                onCheckedChange = {
                    onDarkThemeChange(it)
                },
            )
        },
    )
}

@Composable
fun ColorsSelection(
    onSelectSessionColor: (Long) -> Unit,
    onSelectShortBreakColor: (Long) -> Unit,
    onSelectLongBreakColor: (Long) -> Unit,
    currentSessionColor: Long,
    currentShortBreakColor: Long,
    currentLongBreakColor: Long,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ColorCard(
            color = currentSessionColor,
            onClick = onSelectSessionColor,
        )
        ColorCard(
            color = currentShortBreakColor,
            onClick = onSelectShortBreakColor,
        )
        ColorCard(
            color = currentLongBreakColor,
            onClick = onSelectLongBreakColor,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ThemeSettingPreview() {
    ThemeSetting(
        onExpand = {},
        expanded = { it == "Theme" },
        showColorDialog = false,
        selectedColorCardTitle = "Focus Session",
        onColorCardTitleChange = {},
        onShowColorDialog = {},
        darkTheme = false,
        onDarkThemeChange = {},
        currentShortBreakColor = 0xFFE57373,     // rojo suave
        currentLongBreakColor = 0xFF64B5F6,      // azul suave
        currentSessionColor = 0xFF81C784,        // verde suave
        onSelectColor = { _, _ -> },
    )
}
