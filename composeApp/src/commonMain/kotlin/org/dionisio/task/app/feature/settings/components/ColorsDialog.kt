package org.dionisio.task.app.feature.settings.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.dionisio.task.app.data.presentation.theme.Blue
import org.dionisio.task.app.data.presentation.theme.Green
import org.dionisio.task.app.data.presentation.theme.LightBlue
import org.dionisio.task.app.data.presentation.theme.LightGreen
import org.dionisio.task.app.data.presentation.theme.LongBreakColor
import org.dionisio.task.app.data.presentation.theme.Orange
import org.dionisio.task.app.data.presentation.theme.Pink
import org.dionisio.task.app.data.presentation.theme.Red
import org.dionisio.task.app.data.presentation.theme.SessionColor
import org.dionisio.task.app.data.presentation.theme.ShortBreakColor
import org.dionisio.task.app.data.presentation.theme.Yellow
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ColorsDialog(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onSelectColor: (Long) -> Unit,
    title: String,
) {
    AlertDialog(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        icon = {},
        containerColor = MaterialTheme.colorScheme.background,
        onDismissRequest = onDismiss,
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
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(sessionColors) {
                    ColorCard(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .size(48.dp),
                        color = it,
                        onClick = onSelectColor,
                    )
                }
            }
        },
        dismissButton = {},
        confirmButton = {},
    )
}

@Composable
fun ColorCard(modifier: Modifier = Modifier,
              color: Long,
              onClick: (Long) -> Unit) {
    Box(
        modifier = modifier
            .size(32.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(Color(color))
            .clickable {
                onClick(color)
            },
    )
}

private val sessionColors = listOf(
    SessionColor,
    ShortBreakColor,
    LongBreakColor,
    Red,
    Green,
    Orange,
    Blue,
    Green,
    LightGreen,
    Yellow,
    LightBlue,
    Pink,
)

@Preview(showBackground = true)
@Composable
fun ColorsDialogPreview() {
    MaterialTheme {
        ColorsDialog(
            onDismiss = {},
            onSelectColor = {},
            title = "Choose Color"
        )
    }
}
