package org.dionisio.task.app.core.utils.platform

import kotlinx.datetime.LocalDateTime

expect object DateTimeParser {
    fun parse(dateTimeString: String): LocalDateTime
    fun format(dateTime: LocalDateTime): String
}