package com.example.ravtecnologia.ui.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.format.DateTimeFormatter

@Composable
fun ActivityListScreen(
    title: String, // título opcional da tela
    activities: List<ActivityModel>, // lista de atividades exibidas
    onStart: (Int) -> Unit, // callback ao clicar em "Começar"
    onComplete: (Int) -> Unit, // callback ao clicar em "Concluir"
    onDelete: (Int) -> Unit // callback ao clicar em "Excluir"
) {
    Column(modifier = Modifier.fillMaxSize()) {

        // mostra mensagem se a lista estiver vazia
        if (activities.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Nenhuma atividade encontrada.")
            }
        } else {
            // exibe lista de atividades
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(activities) { activity ->
                    // cada item é um card simples com botões
                    ActivityCardRow(
                        activity = activity,
                        onStart = { onStart(activity.id) },
                        onComplete = { onComplete(activity.id) },
                        onDelete = { onDelete(activity.id) }
                    )
                }
            }
        }
    }
}

@Composable
private fun ActivityCardRow(
    activity: ActivityModel,
    onStart: () -> Unit,
    onComplete: () -> Unit,
    onDelete: () -> Unit
) {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm") // formata data

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(activity.titulo, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(activity.descricao, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Prazo: ${activity.dataLimite.format(formatter)}", style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                // botão "Começar" aparece só se estiver pendente
                if (activity.status == "pendente") {
                    Button(onClick = onStart) { Text("Começar") }
                }

                // botão "Concluir" aparece se não estiver concluída
                if (activity.status != "concluida") {
                    Button(onClick = onComplete) { Text("Concluir") }
                }

                // botão "Excluir" sempre disponível
                OutlinedButton(onClick = onDelete) { Text("Excluir") }
            }
        }
    }
}
