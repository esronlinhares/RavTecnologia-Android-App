package com.example.ravtecnologia.ui.list

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ravtecnologia.data.entity.ActivityEntity
import java.time.LocalDateTime

@Composable
fun InProgressScreen(
    activities: List<ActivityEntity>,
    onComplete: ((ActivityEntity) -> Unit)? = null,
    onDelete: ((ActivityEntity) -> Unit)? = null
) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Todas", "Atrasadas")

    val filteredActivities = when (selectedTab) {
        1 -> activities.filter { it.dataLimite.isBefore(LocalDateTime.now()) } // atrasadas
        else -> activities
    }

    Column {
        TabRow(selectedTabIndex = selectedTab) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(title) }
                )
            }
        }

        if (filteredActivities.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Nenhuma atividade em andamento")
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(filteredActivities) { activity ->
                    ActivityCard(
                        activity = activity,
                        showStart = false,
                        showComplete = true,
                        onComplete = onComplete,
                        onDelete = { onDelete?.invoke(activity) } // corrige chamada
                    )
                }
            }
        }
    }
}
