package org.dionisio.task.app.feature.settings.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.HourglassEmpty
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.dionisio.task.app.data.presentation.component.BloomInputTextField
import org.dionisio.task.app.data.utils.TextFieldState
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun FocusSessionsSetting(
    focusSessionMinutes: Int,
    onFocusSessionMinutesChange: (String) -> Unit,
    shortBreakMinutes: Int,
    onShortBreakMinutesChange: (String) -> Unit,
    longBreakMinutes: Int,
    onLongBreakMinutesChange: (String) -> Unit,
    onExpand: (String) -> Unit,
    expanded: (String) -> Boolean,
) {
    SettingCard(
        onExpand = {
            onExpand("Focus Sessions")
        },
        expanded = expanded("Focus Sessions"),
        title = "Focus Sessions",
        icon = Icons.Outlined.HourglassEmpty,
        content = {
            var autoStartBreaks by remember { mutableStateOf(false) }
            var autoStartFocusSession by remember { mutableStateOf(false) }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                SessionTime(
                    modifier = Modifier.weight(1f),
                    title = "Session",
                    currentValue = focusSessionMinutes.toString(),
                    onValueChange = {
                        onFocusSessionMinutesChange(it)
                    },
                )
                SessionTime(
                    modifier = Modifier.weight(1f),
                    title = "Short Break",
                    currentValue = shortBreakMinutes.toString(),
                    onValueChange = {
                        onShortBreakMinutesChange(it)
                    },
                )
                SessionTime(
                    modifier = Modifier.weight(1f),
                    title = "Long Break",
                    currentValue = longBreakMinutes.toString(),
                    onValueChange = {
                        onLongBreakMinutesChange(it)
                    },
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            AutoStartSession(
                title = "Auto Start Breaks",
                checked = autoStartBreaks,
                onCheckedChange = {
                    autoStartBreaks = it
                },
            )
            Spacer(modifier = Modifier.height(12.dp))
            AutoStartSession(
                title = "Auto Start Sessions",
                checked = autoStartFocusSession,
                onCheckedChange = {
                    autoStartFocusSession = it
                },
            )
        },
    )
}

@Composable
fun SessionTime(
    modifier: Modifier = Modifier,
    title: String,
    currentValue: String,
    onValueChange: (String) -> Unit,
) {
    BloomInputTextField(
        modifier = modifier,
        textStyle = MaterialTheme.typography.bodyMedium.copy(
            textAlign = TextAlign.Start,
        ),
        label = {
            Text(
                text = title,
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.SemiBold,
                ),
            )
        },
        value = TextFieldState(currentValue),
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number,
        ),
    )
}

@Composable
fun SettingCard(
    title: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
    onExpand: () -> Unit,
    expanded: Boolean,
) {
    Card(
        modifier = modifier,
        onClick = {
            onExpand()
        },
    ) {
        Column(
            modifier = modifier.padding(16.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                    )
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleLarge,
                    )
                }

                IconButton(onClick = { onExpand() }) {
                    Icon(
                        imageVector = if (expanded) {
                            Icons.Rounded.KeyboardArrowUp
                        } else {
                            Icons.Rounded.KeyboardArrowDown
                        },
                        contentDescription = null,
                    )
                }
            }
            AnimatedVisibility(expanded) {
                Column {
                    Spacer(modifier = Modifier.height(8.dp))
                    content()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FocusSessionsSettingPreview() {
    FocusSessionsSetting(
        focusSessionMinutes = 25,
        onFocusSessionMinutesChange = {},

        shortBreakMinutes = 5,
        onShortBreakMinutesChange = {},

        longBreakMinutes = 15,
        onLongBreakMinutesChange = {},

        onExpand = {},
        expanded = { it == "Focus Sessions" },
    )
}
