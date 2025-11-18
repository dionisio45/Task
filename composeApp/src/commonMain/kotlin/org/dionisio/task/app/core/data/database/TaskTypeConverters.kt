package org.dionisio.task.app.core.data.database

import androidx.room.TypeConverter
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.json.Json
import org.dionisio.task.app.core.utils.platform.DateTimeParser

object TaskTypeConverters {

    // Conversión LocalDateTime ↔ String
    @TypeConverter
    fun fromLocalDateTime(value: String?): LocalDateTime? {
        return value?.let { DateTimeParser.parse(it) }
    }

    @TypeConverter
    fun toLocalDateTime(dateTime: LocalDateTime?): String? {
        return dateTime?.let { DateTimeParser.format(it) }
    }

    // Conversión List<String> ↔ String
    private val json = Json { ignoreUnknownKeys = true; encodeDefaults = true }

    @TypeConverter
    fun fromStringList(value: String): List<String> {
        return json.decodeFromString(value)
    }

    @TypeConverter
    fun toStringList(list: List<String>): String {
        return json.encodeToString(list)
    }
}

