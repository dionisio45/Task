package org.dionisio.task.app.core.utils.platform

import android.os.Build
import androidx.annotation.RequiresApi
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.number
import java.time.format.DateTimeFormatter
import java.time.LocalDateTime as JvmLocalDateTime

actual object DateTimeParser {

    @RequiresApi(Build.VERSION_CODES.O)
    actual fun parse(dateTimeString: String): LocalDateTime {
        val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
        val jvmDateTime = JvmLocalDateTime.parse(dateTimeString, formatter)
        return LocalDateTime(
            jvmDateTime.year,
            jvmDateTime.monthValue,
            jvmDateTime.dayOfMonth,
            jvmDateTime.hour,
            jvmDateTime.minute,
            jvmDateTime.second,
            jvmDateTime.nano
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    actual fun format(dateTime: LocalDateTime): String {
        val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
        val jvmDateTime = JvmLocalDateTime.of(
            dateTime.year,
            dateTime.month.number,
            dateTime.dayOfMonth,
            dateTime.hour,
            dateTime.minute,
            dateTime.second,
            dateTime.nanosecond
        )
        return jvmDateTime.format(formatter)
    }
}
