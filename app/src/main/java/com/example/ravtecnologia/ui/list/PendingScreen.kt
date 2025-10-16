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
fun PendingScreen(
    activities: List<ActivityEntity>,
    onStart: ((ActivityEntity) -> Unit)? = null,
    onComplete: ((ActivityEntity) -> Unit)? = null,
    onDelete: ((ActivityEntity) -> Unit)? = null
) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Todas", "Atrasadas")

    val filteredActivities = when (selectedTab) {
        1 -> activities.filter { it.dataLimite.isBefore(LocalDateTime.now()) }
        else -> activities
    }

    Column {
        TabRow(selectedTabIndex = selectedTab) {
            tabs.forEachIndexed { index, title ->
                Tab(selected = selectedTab == index, onClick = { selectedTab = index }, text = { Text(title) })
            }
        }

        if (filteredActivities.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Nenhuma atividade")
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
                        showStart = true,
                        showComplete = true,
                        onStart = onStart,
                        onComplete = onComplete,
                        onDelete = onDelete
                    )
                }
            }
        }
    }
}
