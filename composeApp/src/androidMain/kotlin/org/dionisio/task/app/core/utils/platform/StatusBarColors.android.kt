package org.dionisio.task.app.core.utils.platform

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat

@Composable
actual fun StatusBarColors(
    statusBarColor: Color,
    navBarColor: Color
) {
    val view = LocalView.current
    val activity = view.context as? Activity ?: return
    val window = activity.window

    LaunchedEffect(statusBarColor, navBarColor) {
        // Hacer que el contenido no se dibuje debajo del sistema
        WindowCompat.setDecorFitsSystemWindows(window, false)

        // Ajustar colores del sistema
        window.statusBarColor = statusBarColor.toArgb()
        window.navigationBarColor = navBarColor.toArgb()

        // Ajustar el contraste (claro/oscuro)
        val isLight = statusBarColor.luminance() > 0.5f
        val insetsController = WindowInsetsControllerCompat(window, window.decorView)
        insetsController.isAppearanceLightStatusBars = isLight
        insetsController.isAppearanceLightNavigationBars = isLight
    }
}
