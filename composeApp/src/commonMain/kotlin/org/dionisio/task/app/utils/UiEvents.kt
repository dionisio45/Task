package org.dionisio.task.app.utils

sealed class UiEvents {
    data class ShowSnackbar(val message: String) : UiEvents()
    data object NavigateBack : UiEvents()
}
