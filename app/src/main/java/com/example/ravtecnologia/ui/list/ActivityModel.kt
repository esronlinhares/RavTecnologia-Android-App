package com.example.ravtecnologia.ui.list

import java.time.LocalDateTime

data class ActivityModel(
    val id: Int = 0,
    val titulo: String,
    val descricao: String,
    val status: String,
    val dataLimite: LocalDateTime,
    val imagemUri: String? = null,
    val concluidoEm: LocalDateTime? = null

)