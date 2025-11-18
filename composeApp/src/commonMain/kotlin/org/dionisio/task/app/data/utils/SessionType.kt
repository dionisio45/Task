package org.dionisio.task.app.data.utils

sealed class SessionType {
    data object Focus : SessionType()
    data object ShortBreak : SessionType()
    data object LongBreak : SessionType()
}
