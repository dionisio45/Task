package org.dionisio.task.app.feature.addtask.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.LocalTime
import org.dionisio.task.app.utils.formattedTimeBasedOnTimeFormat
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import task.composeapp.generated.resources.Res
import task.composeapp.generated.resources.end_time
import task.composeapp.generated.resources.start_time

@Composable
fun TimeComponent(
    title: String,
    icon: DrawableResource,
    iconColor: Color,
    iconSize: Int = 32,
    time: LocalTime,
    hourFormat: Int,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier.clickable {
            onClick()
        },
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
            ),
        )
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = CenterVertically,
        ) {
            Text(
                text = time.formattedTimeBasedOnTimeFormat(hourFormat),
                style = MaterialTheme.typography.titleSmall.copy(
                    fontSize = 16.sp,
                ),
            )

            Icon(
                modifier = Modifier
                    .size(iconSize.dp),
                painter = painterResource(icon),
                contentDescription = title,
                tint = iconColor,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TimeComponentPreview() {
    MaterialTheme {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            TimeComponent(
                title = "Start Time",
                icon = Res.drawable.start_time,
                iconColor = Color(0xFF3375fd),
                time = LocalTime(9, 30),
                hourFormat = 24,
                onClick = {}
            )
        }
    }
}