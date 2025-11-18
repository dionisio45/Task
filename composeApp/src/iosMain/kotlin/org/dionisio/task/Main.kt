package org.dionisio.task

import androidx.compose.ui.window.ComposeUIViewController
import org.dionisio.task.app.core.di.initKoin

fun MainViewController() = ComposeUIViewController {
    initKoin ()
    App()
}