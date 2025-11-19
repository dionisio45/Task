package org.dionisio.task.app.feature.taskprogress.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import task.composeapp.generated.resources.Res
import task.composeapp.generated.resources.ic_complete

@Composable
fun SuccessfulCompletionOfTask(
    modifier: Modifier = Modifier,
    title: String,
    message: String,
    onConfirm: () -> Unit,
) {
    AlertDialog(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        icon = {
            Image(
                modifier = Modifier.size(48.dp),
                painter = painterResource(
                    Res.drawable.ic_complete
                ),
                contentDescription = "Task Completed",
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
        onDismissRequest = onConfirm,
        title = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    textAlign = TextAlign.Center,
                ),
            )
        },
        text = {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = message,
                style = MaterialTheme.typography.bodyMedium.copy(
                    textAlign = TextAlign.Center,
                ),
            )
        },
        dismissButton = {},
        confirmButton = {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = onConfirm,
            ) {
                Text(
                    text = "OK",
                    style = MaterialTheme.typography.titleSmall,
                )
            }
        },
    )
}

@Preview
@Composable
fun SuccessfulCompletionOfTaskPreview() {
    MaterialTheme {
        SuccessfulCompletionOfTask(
            title = "¡Tarea completada!",
            message = "Has finalizado todas las sesiones programadas.",
            onConfirm = {}
        )
    }
}
