package com.example.ravtecnologia.ui.list

import java.time.LocalDateTime

// modelo simplificado de atividade para exibição na interface
data class ActivityModel(
    val id: Int = 0,
    val titulo: String, // título da atividade
    val descricao: String, // descrição da tarefa
    val status: String, // pendente, andamento ou concluída
    val dataLimite: LocalDateTime, // data e hora limite
    val imagemUri: String? = null, // caminho opcional da imagem
    val concluidoEm: LocalDateTime? = null // data de conclusão
)
