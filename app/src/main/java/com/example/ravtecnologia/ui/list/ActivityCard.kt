package com.example.ravtecnologia.ui.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.ravtecnologia.data.entity.ActivityEntity
import java.time.LocalDateTime
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.Alignment

@Composable
fun ActivityCard(
    activity: ActivityEntity, // atividade que será exibida
    showStart: Boolean = true, // controla exibição do botão "Começar"
    showComplete: Boolean = true, // controla exibição do botão "Concluir"
    onStart: ((ActivityEntity) -> Unit)? = null, // callback para iniciar
    onComplete: ((ActivityEntity) -> Unit)? = null, // callback para concluir
    onDelete: ((ActivityEntity) -> Unit)? = null, // callback para deletar
    onChangeStatus: ((ActivityEntity, String) -> Unit)? = null // callback para mudar status
) {
    // verifica se a atividade está atrasada (prazo passou e não concluída)
    val isOverdue = activity.dataLimite.isBefore(LocalDateTime.now()) && activity.status != "Concluída"

    // define cor do card conforme status
    val cardColor = when {
        activity.status == "Concluída" -> Color(0xFFD0F0C0) // verde claro
        isOverdue -> Color(0xFFFFCDD2) // vermelho claro
        else -> Color(0xFFFFFFFF) // branco padrão
    }

    // Card principal da atividade
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            // título da atividade
            Text(text = activity.titulo, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(4.dp))

            // descrição da atividade
            Text(text = activity.descricao, style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(4.dp))

            // data limite formatada
            Text(
                text = "Data limite: ${activity.dataLimite.dayOfMonth}/${activity.dataLimite.monthValue}/${activity.dataLimite.year} ${activity.dataLimite.hour}:${activity.dataLimite.minute.toString().padStart(2,'0')}"
            )

            // mostra data de conclusão se houver
            activity.concluidoEm?.let {
                Text(text = "Concluída em: ${it.dayOfMonth}/${it.monthValue}/${it.year} ${it.hour}:${it.minute}")
            }

            // exibe imagem da atividade se houver URI
            activity.imagemUri?.let { uri ->
                Spacer(Modifier.height(8.dp))
                AsyncImage(
                    model = uri,
                    contentDescription = "Imagem da atividade",
                    modifier = Modifier
                        .height(150.dp)
                        .wrapContentWidth()
                        .align(Alignment.Start),
                    contentScale = ContentScale.Fit // mantém proporção
                )
            }

            Spacer(Modifier.height(8.dp))

            // linha com botões de ação
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {

                // botão "Começar"
                if (showStart && onStart != null) {
                    Button(onClick = { onStart(activity) }) { Text("Começar") }
                }

                // botão "Concluir"
                if (showComplete && onComplete != null) {
                    Button(onClick = { onComplete(activity) }) { Text("Concluir") }
                }

                // botões de mudança de status (dependendo do status atual)
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

                // botão "Excluir" (sempre no fim)
                if (onDelete != null) {
                    Button(
                        onClick = { onDelete(activity) },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF8A80)) // vermelho
                    ) {
                        Text("Excluir", color = Color.White)
                    }
                }
            }
        }
    }
}
