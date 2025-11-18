package org.dionisio.task.app.feature.settings.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun NotificationsSetting(
    onExpand: (String) -> Unit,
    expanded: (String) -> Boolean,
    remindersOn: Boolean,
    onRemindersChange: (Boolean) -> Unit,
) {
    SettingCard(
        onExpand = {
            onExpand("Notifications")
        },
        expanded = expanded("Notifications"),
        title = "Notifications",
        icon = Icons.Outlined.Notifications,
        content = {
            AutoStartSession(
                title = "Reminders",
                checked = remindersOn,
                onCheckedChange = onRemindersChange,
            )
        },
    )
}

@Composable
fun AutoStartSession(title: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = title)
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NotificationsSettingPreview() {
    NotificationsSetting(
        expanded = { it == "Notifications"  },
        onExpand = {},
        remindersOn = true,
        onRemindersChange = {},
    )
}
