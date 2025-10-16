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
    activity: ActivityEntity,
    showStart: Boolean = true,
    showComplete: Boolean = true,
    onStart: ((ActivityEntity) -> Unit)? = null,
    onComplete: ((ActivityEntity) -> Unit)? = null,
    onDelete: ((ActivityEntity) -> Unit)? = null,
    onChangeStatus: ((ActivityEntity, String) -> Unit)? = null // para mudar status
) {
    val isOverdue = activity.dataLimite.isBefore(LocalDateTime.now()) && activity.status != "Concluída"
    val cardColor = when {
        activity.status == "Concluída" -> Color(0xFFD0F0C0)
        isOverdue -> Color(0xFFFFCDD2)
        else -> Color(0xFFFFFFFF)
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = activity.titulo, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(4.dp))
            Text(text = activity.descricao, style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(4.dp))
            Text(
                text = "Data limite: ${activity.dataLimite.dayOfMonth}/${activity.dataLimite.monthValue}/${activity.dataLimite.year} ${activity.dataLimite.hour}:${activity.dataLimite.minute.toString().padStart(2,'0')}"
            )

            activity.concluidoEm?.let {
                Text(text = "Concluída em: ${it.dayOfMonth}/${it.monthValue}/${it.year} ${it.hour}:${it.minute}")
            }

            activity.imagemUri?.let { uri ->
                Spacer(Modifier.height(8.dp))
                AsyncImage(
                    model = uri,
                    contentDescription = "Imagem da atividade",
                    modifier = Modifier
                        .height(150.dp)           // define altura fixa
                        .wrapContentWidth()       // largura mínima, não ocupa toda a tela
                        .align(Alignment.Start),  // alinha à esquerda dentro da coluna
                    contentScale = ContentScale.Fit // mantém proporção original
                )
            }


            Spacer(Modifier.height(8.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                if (showStart && onStart != null) {
                    Button(onClick = { onStart(activity) }) { Text("Começar") }
                }
                if (showComplete && onComplete != null) {
                    Button(onClick = { onComplete(activity) }) { Text("Concluir") }
                }

                // Botões de mudança de status
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

                // Botão de exclusão (por último)
                if (onDelete != null) {
                    Button(
                        onClick = { onDelete(activity) },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF8A80))
                    ) {
                        Text("Excluir", color = Color.White)
                    }
                }
            }
        }
    }
}
