package org.dionisio.task.app.feature.settings.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Timer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.dionisio.task.app.data.presentation.component.BloomDropDown
import org.dionisio.task.app.data.utils.TextFieldState
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun TimeSetting(
    onExpand: (String) -> Unit,
    expanded: (String) -> Boolean,
    hourFormats: List<String>,
    selectedHourFormat: Int,
    onHourFormatChange: (Int) -> Unit,
) {
    SettingCard(
        onExpand = {
            onExpand("Time")
        },
        expanded = expanded("Time"),
        title = "Time",
        icon = Icons.Outlined.Timer,
        content = {
            SoundSelection(
                title = "Hour Format",
                options = hourFormats,
                selectedOption = hourFormats.getOrNull(selectedHourFormat) ?: "12-hour",
                onSelectOption = { selectedString ->
                    val index = hourFormats.indexOf(selectedString)
                    if (index != -1) {
                        onHourFormatChange(index)
                    }
                    onExpand("Time")
                },
            )
        },
    )
}

@Composable
fun SoundSelection(
    options: List<String>,
    title: String,
    selectedOption: String,
    onSelectOption: (String) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(.5f),
            text = title,
        )
        BloomDropDown(
            modifier = Modifier
                .fillMaxWidth(),
            options = options,
            selectedOption = TextFieldState(text = selectedOption),
            onOptionSelected = {
                onSelectOption(it)
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TimeSettingPreview() {
    TimeSetting(
        onExpand = {},
        expanded = { it == "Time" },
        hourFormats = listOf("12-hour", "24-hour"),
        selectedHourFormat = 0,
        onHourFormatChange = {},
    )
}
