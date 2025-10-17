package com.example.ravtecnologia.ui.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ravtecnologia.data.entity.ActivityEntity

@Composable
fun CompletedScreen(
    activities: List<ActivityEntity>, // lista de atividades concluídas
    onDelete: ((ActivityEntity) -> Unit)? = null, // ação de excluir
    onChangeStatus: ((ActivityEntity, String) -> Unit)? = null // alterar status, se precisar
) {
    if (activities.isEmpty()) {
        // mensagem se não houver atividades concluídas
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Nenhuma atividade concluída")
        }
    } else {
        // lista as atividades concluídas
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // cada item é um ActivityCard
            items(activities) { activity ->
                ActivityCard(
                    activity = activity,
                    showStart = false,     // não mostra botão "Começar"
                    showComplete = false,  // nem "Concluir"
                    onChangeStatus = onChangeStatus,
                    onDelete = { onDelete?.invoke(activity) } // permite excluir
                )
            }
        }
    }
}
