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
    activities: List<ActivityEntity>,
    onDelete: ((ActivityEntity) -> Unit)? = null,
    onChangeStatus: ((ActivityEntity, String) -> Unit)? = null
) {
    if (activities.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text("Nenhuma atividade concluída")
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(activities) { activity ->
                ActivityCard(
                    activity = activity,
                    showStart = false,
                    showComplete = false,
                    onChangeStatus = onChangeStatus,
                    onDelete = { onDelete?.invoke(activity) }
                )
            }
        }
    }
}


