package com.example.ravtecnologia.ui.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.ravtecnologia.data.entity.ActivityEntity
import java.time.LocalDateTime

@Composable
fun ActivityCard(
    activity: ActivityEntity,
    showStart: Boolean = true,
    showComplete: Boolean = true,
    onStart: ((ActivityEntity) -> Unit)? = null,
    onComplete: ((ActivityEntity) -> Unit)? = null,
    onDelete: ((ActivityEntity) -> Unit)? = null,
    onChangeStatus: ((ActivityEntity, String) -> Unit)? = null // permite mudar status
) {
    // controla se o diálogo de exclusão deve aparecer
    var showDeleteDialog by remember { mutableStateOf(false) }

    // verifica se a tarefa está atrasada e ainda não concluída
    val isOverdue = activity.dataLimite.isBefore(LocalDateTime.now()) && activity.status != "Concluída"

    // define a cor do card dependendo do status da atividade
    val cardColor = when {
        activity.status == "Concluída" -> Color(0xFFD0F0C0) // verde claro
        isOverdue -> Color(0xFFFFCDD2) // vermelho claro
        else -> Color(0xFFFFFFFF) // branco
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            // Título da atividade
            Text(text = activity.titulo, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(4.dp))

            // Descrição da atividade
            Text(text = activity.descricao, style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(4.dp))

            // Data limite formatada
            Text(
                text = "Data limite: ${activity.dataLimite.dayOfMonth}/${activity.dataLimite.monthValue}/${activity.dataLimite.year} ${activity.dataLimite.hour}:${activity.dataLimite.minute.toString().padStart(2,'0')}"
            )

            // Exibe data de conclusão, se houver
            activity.concluidoEm?.let {
                Text(text = "Concluída em: ${it.dayOfMonth}/${it.monthValue}/${it.year} ${it.hour}:${it.minute}")
            }

            // Exibe imagem, se houver
            activity.imagemUri?.let { uri ->
                Spacer(Modifier.height(8.dp))
                AsyncImage(
                    model = uri,
                    contentDescription = "Imagem da atividade",
                    modifier = Modifier
                        .height(150.dp)
                        .wrapContentWidth()
                        .align(Alignment.Start),
                    contentScale = ContentScale.Fit
                )
            }

            Spacer(Modifier.height(8.dp))

            // Botões de ação
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                // Botão "Começar"
                if (showStart && onStart != null) {
                    Button(onClick = { onStart(activity) }) { Text("Começar") }
                }

                // Botão "Concluir"
                if (showComplete && onComplete != null) {
                    Button(onClick = { onComplete(activity) }) { Text("Concluir") }
                }

                // Botões para mudar status
                onChangeStatus?.let { change ->
                    when (activity.status) {
                        "Concluída" -> {
                            Button(onClick = { change(activity, "Pendente") }) { Text("Pendente") }
                            Button(onClick = { change(activity, "Em andamento") }) { Text("Em andamento") }
                        }
                        "Em andamento" -> {
                            Button(onClick = { change(activity, "Pendente") }) { Text("Pendente") }
                        }
                        "Pendente" -> {
                            Button(onClick = { change(activity, "Em andamento") }) { Text("Em andamento") }
                        }
                    }
                }

                // Botão de exclusão abre o diálogo
                if (onDelete != null) {
                    Button(
                        onClick = { showDeleteDialog = true },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF8A80))
                    ) {
                        Text("Excluir", color = Color.White)
                    }
                }
            }
        }
    }

    // ----- ALERTA DE CONFIRMAÇÃO DE EXCLUSÃO -----
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false }, // fecha se clicar fora
            title = { Text("Confirmação") },
            text = { Text("Deseja realmente excluir esta atividade?") },
            confirmButton = {
                TextButton(onClick = {
                    onDelete?.invoke(activity) // executa exclusão
                    showDeleteDialog = false
                }) {
                    Text("Sim")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Não")
                }
            }
        )
    }
}