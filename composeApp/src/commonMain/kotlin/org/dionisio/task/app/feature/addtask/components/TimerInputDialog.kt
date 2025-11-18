package org.dionisio.task.app.feature.addtask.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import kotlinx.datetime.LocalTime
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimerInputDialog(
    title: String,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    state: TimePickerState,
    onConfirmStartTime: (LocalTime) -> Unit,
) {
    AlertDialog(
        properties = DialogProperties(usePlatformDefaultWidth = true),
        modifier = modifier,
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
            )
        },
        text = {
            TimeInput(
                modifier = Modifier.fillMaxWidth(),
                state = state,
            )
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                content = {
                    Text(text = "Cancel")
                },
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmStartTime(
                        LocalTime(
                            hour = state.hour,
                            minute = state.minute,
                        ),
                    )
                    onDismiss()
                },
                content = {
                    Text(text = "OK")
                },
            )
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun TimerInputDialogPreview() {
    MaterialTheme {
        var showDialog by remember { mutableStateOf(true) }
        val timePickerState = rememberTimePickerState(
            initialHour = 9,
            initialMinute = 30,
            is24Hour = true
        )

        Surface(modifier = Modifier.padding(24.dp)) {
            if (showDialog) {
                TimerInputDialog(
                    title = "Select Time",
                    onDismiss = { showDialog = false },
                    state = timePickerState,
                    onConfirmStartTime = { time ->
                        println("Hora seleccionada: $time")
                    }
                )
            }

            Button(onClick = { showDialog = true }) {
                Text("Mostrar TimePicker")
            }
        }
    }
}