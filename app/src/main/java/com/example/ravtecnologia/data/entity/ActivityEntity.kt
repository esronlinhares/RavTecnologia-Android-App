package com.example.ravtecnologia.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "activities") // Define a tabela no banco
data class ActivityEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // ID auto incrementado
    val titulo: String,                               // Título da atividade
    val descricao: String,                            // Descrição detalhada
    val dataLimite: LocalDateTime,                    // Data e hora limite para concluir
    val status: String = "Pendente",                 // Status inicial padrão
    val imagemUri: String? = null,                   // Caminho da imagem (opcional)
    val concluidoEm: LocalDateTime? = null           // Data de conclusão (opcional)
)
