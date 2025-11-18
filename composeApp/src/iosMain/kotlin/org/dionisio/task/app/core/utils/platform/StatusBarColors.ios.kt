package org.dionisio.task.app.core.utils.platform

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
actual fun StatusBarColors(
    statusBarColor: Color,
    navBarColor: Color
) {
    // No-op en iOS: UIKit no permite cambiar status bar/nav bar desde Compose
}
