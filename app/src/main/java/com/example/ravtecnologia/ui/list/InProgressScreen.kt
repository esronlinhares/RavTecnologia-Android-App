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
import java.time.LocalDateTime
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember



@Composable
fun InProgressScreen(
    activities: List<ActivityEntity>,
    onComplete: ((ActivityEntity) -> Unit)? = null,
    onDelete: ((ActivityEntity) -> Unit)? = null,
    onChangeStatus: ((ActivityEntity, String) -> Unit)? = null // novo parâmetro
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
                        showStart = false, // já estão em andamento
                        showComplete = true,
                        onComplete = onComplete,
                        onDelete = onDelete,
                        onChangeStatus = onChangeStatus // passa o parâmetro
                    )
                }
            }
        }
    }
}
