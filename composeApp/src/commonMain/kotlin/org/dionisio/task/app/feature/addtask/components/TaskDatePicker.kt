package org.dionisio.task.app.feature.addtask.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalDateTime
import org.dionisio.task.app.utils.selectedDateMillisToLocalDateTime
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskDatePicker(
    datePickerState: DatePickerState,
    dismiss: () -> Unit,
    onConfirmDate: (LocalDateTime) -> Unit,
) {
    DatePickerDialog(
        onDismissRequest = { dismiss() },
        dismissButton = {
            TextButton(onClick = dismiss) {
                Text(text = "Cancel")
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmDate(datePickerState.selectedDateMillis.selectedDateMillisToLocalDateTime())
                    dismiss()
                },
            ) {
                Text(text = "OK")
            }
        },
    ) {
        DatePicker(state = datePickerState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun TaskDatePickerPreview() {
    MaterialTheme {
        var showDialog by remember { mutableStateOf(true) }
        val datePickerState = rememberDatePickerState()

        Surface {
            if (showDialog) {
                TaskDatePicker(
                    datePickerState = datePickerState,
                    dismiss = { showDialog = false },
                    onConfirmDate = { selectedDate: LocalDateTime ->
                        println("Fecha seleccionada: $selectedDate")
                    }
                )
            }

            Button(
                onClick = { showDialog = true },
                modifier = androidx.compose.ui.Modifier
                    .padding(24.dp)
            ) {
                Text("Mostrar DatePicker")
            }
        }
    }
}