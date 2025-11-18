package org.dionisio.task.app.feature.addtask

sealed class UiEvents {
    data class ShowSnackbar(val message: String) : UiEvents()
    data object NavigateBack : UiEvents()
}