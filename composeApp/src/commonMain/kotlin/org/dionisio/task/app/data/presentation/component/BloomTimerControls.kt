package org.dionisio.task.app.data.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.dionisio.task.app.feature.taskprogress.TimerState
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun BloomTimerControls(
    modifier: Modifier = Modifier,
    state: TimerState,
    onClickReset: () -> Unit,
    onClickNext: () -> Unit,
    onClickAction: (state: TimerState) -> Unit,
) {
    Row(
        modifier = modifier.background(Color.Transparent),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        IconButton(onClick = onClickReset) {
            Icon(
                modifier = Modifier.size(120.dp),
                imageVector = Icons.Filled.Replay,
                contentDescription = "Reset Timer",
                tint = MaterialTheme.colorScheme.onBackground,
            )
        }

        BloomCircleButton(
            modifier = Modifier.size(84.dp),
            icon = {
                Icon(
                    modifier = Modifier.size(48.dp),
                    imageVector = when (state) {
                        TimerState.Paused -> {
                            Icons.Filled.PlayArrow
                        }

                        TimerState.Ticking -> {
                            Icons.Filled.Pause
                        }

                        TimerState.Finished -> {
                            Icons.Filled.Replay
                        }

                        else -> {
                            Icons.Filled.PlayArrow
                        }
                    },
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                )
            },
            onClick = {
                onClickAction(state)
            },
            color = MaterialTheme.colorScheme.primary,
        )

        IconButton(onClick = onClickNext) {
            Icon(
                modifier = Modifier.size(120.dp),
                imageVector = Icons.Filled.SkipNext,
                contentDescription = "Next Timer",
                tint = MaterialTheme.colorScheme.onBackground,
            )
        }
    }
}

// Ejemplo de estado del temporizador (enum)
/*enum class TimerState1 {
    Paused,
    Ticking,
    Finished
}*/

@Preview(showBackground = true)
@Composable
fun BloomTimerControlsPreview() {
    MaterialTheme {
        BloomTimerControls(
            modifier = Modifier.fillMaxWidth(),
            state = TimerState.Paused,
            onClickReset = { /* Acción de preview */ },
            onClickNext = { /* Acción de preview */ },
            onClickAction = { _ -> /* Acción de preview */ }
        )
    }
}
