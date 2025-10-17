package com.example.ravtecnologia.data.database

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Converters {
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
    // Define o formato que será usado para converter LocalDateTime em String e vice-versa

    @TypeConverter
    fun fromLocalDateTime(date: LocalDateTime?): String? {
        // Converte LocalDateTime para String antes de salvar no banco
        return date?.format(formatter)
    }

    @TypeConverter
    fun toLocalDateTime(dateString: String?): LocalDateTime? {
        // Converte String do banco de volta para LocalDateTime
        return dateString?.let { LocalDateTime.parse(it, formatter) }
    }
}