package com.example.ravtecnologia.ui

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.ravtecnologia.data.entity.ActivityEntity
import java.io.File
import java.time.LocalDateTime
import java.util.*

@Composable
fun AddActivityDialog(
    onAdd: (ActivityEntity) -> Unit,
    onDismiss: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedDateTime by remember { mutableStateOf<LocalDateTime?>(null) }
    var imageUri by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    // ----- DATE + TIME PICKER -----
    val datePicker = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            calendar.set(year, month, dayOfMonth)
            TimePickerDialog(
                context,
                { _, hourOfDay, minute ->
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    calendar.set(Calendar.MINUTE, minute)
                    selectedDateTime = LocalDateTime.of(
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH) + 1,
                        calendar.get(Calendar.DAY_OF_MONTH),
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE)
                    )
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            ).show()
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    // ----- IMAGE PICKER -----
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            // Cria um arquivo local no armazenamento interno
            val inputStream = context.contentResolver.openInputStream(it)
            val file = File(context.filesDir, "image_${System.currentTimeMillis()}.jpg")
            inputStream?.use { input ->
                file.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
            imageUri = file.absolutePath // salva o caminho real da imagem
        }
    }

    // ----- DIALOG -----
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Nova Atividade") },
        text = {
            Column {
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Título") }
                )
                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Descrição") }
                )
                Spacer(Modifier.height(8.dp))

                // Botão para selecionar imagem
                Button(onClick = { galleryLauncher.launch("image/*") }) {
                    Text(if (imageUri == null) "Adicionar Imagem" else "Trocar Imagem")
                }

                // Preview da imagem selecionada
                imageUri?.let {
                    Spacer(Modifier.height(8.dp))
                    AsyncImage(
                        model = File(it),
                        contentDescription = "Imagem selecionada",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                    )
                }

                Spacer(Modifier.height(8.dp))
                Button(onClick = { datePicker.show() }) {
                    Text(
                        selectedDateTime?.let {
                            "${it.dayOfMonth}/${it.monthValue}/${it.year} ${it.hour}:${it.minute.toString().padStart(2, '0')}"
                        } ?: "Escolher data e hora"
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                if (title.isNotBlank() && selectedDateTime != null) {
                    onAdd(
                        ActivityEntity(
                            titulo = title,
                            descricao = description,
                            dataLimite = selectedDateTime!!,
                            imagemUri = imageUri
                        )
                    )
                    onDismiss()
                }
            }) { Text("Adicionar") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        }
    )
}
