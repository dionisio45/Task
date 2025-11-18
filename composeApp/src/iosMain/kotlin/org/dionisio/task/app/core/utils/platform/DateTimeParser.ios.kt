package org.dionisio.task.app.core.utils.platform

import kotlinx.datetime.LocalDateTime

actual object DateTimeParser {

    actual fun parse(dateTimeString: String): LocalDateTime {
        return LocalDateTime.parse(dateTimeString)
    }

    actual fun format(dateTime: LocalDateTime): String {
        return dateTime.toString()
    }
}