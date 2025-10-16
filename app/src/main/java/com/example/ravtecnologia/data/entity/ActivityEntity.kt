package com.example.ravtecnologia.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "activities")
data class ActivityEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val titulo: String,
    val descricao: String,
    val dataLimite: LocalDateTime,
    val status: String = "Pendente",
    val imagemUri: String? = null,
    val concluidoEm: LocalDateTime? = null
)
