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
    title: String,
    activities: List<ActivityModel>,
    onStart: (Int) -> Unit,
    onComplete: (Int) -> Unit,
    onDelete: (Int) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        // título opcional: se quiser mostrar no topo, descomente
        // Text(text = title, style = MaterialTheme.typography.headlineSmall, modifier = Modifier.padding(16.dp))

        if (activities.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Nenhuma atividade encontrada.")
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(activities) { activity ->
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
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

    Card(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(activity.titulo, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(activity.descricao, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text("Prazo: ${activity.dataLimite.format(formatter)}", style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                // mostrar "Começar" apenas se pendente
                if (activity.status == "pendente") {
                    Button(onClick = onStart) { Text("Começar") }
                }

                // mostrar "Concluir" se pendente ou andamento
                if (activity.status != "concluida") {
                    Button(onClick = onComplete) { Text("Concluir") }
                }

                // excluir sempre disponível
                OutlinedButton(onClick = onDelete) { Text("Excluir") }
            }
        }
    }
}
