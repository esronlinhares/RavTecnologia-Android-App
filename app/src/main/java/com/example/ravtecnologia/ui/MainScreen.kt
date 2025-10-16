package com.example.ravtecnologia.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.*
import com.example.ravtecnologia.data.database.AppDatabase
import com.example.ravtecnologia.data.entity.ActivityEntity
import com.example.ravtecnologia.ui.list.*
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import androidx.navigation.NavHostController

data class BottomNavItem(val route: String, val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val context = LocalContext.current
    val db = AppDatabase.getDatabase(context)
    val activityDao = db.activityDao()
    val scope = rememberCoroutineScope()

    var showAddDialog by remember { mutableStateOf(false) }
    val navController = rememberNavController()

    val activities by activityDao.getAllActivitiesFlow().collectAsState(initial = emptyList())

    val bottomItems = listOf(
        BottomNavItem("pendentes", "Pendentes", Icons.Default.Pending),
        BottomNavItem("andamento", "Em andamento", Icons.Default.HourglassBottom),
        BottomNavItem("concluidas", "Concluídas", Icons.Default.CheckCircle)
    )

    Scaffold(
        topBar = { TopAppBar(title = { Text("Minhas Atividades") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        },
        bottomBar = { BottomNavigationBar(navController, bottomItems) }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            NavHost(navController, startDestination = "pendentes") {
                composable("pendentes") {
                    PendingScreen(
                        activities = activities.filter { it.status == "Pendente" },
                        onStart = { a -> scope.launch { activityDao.updateActivity(a.copy(status = "Em andamento")) } },
                        onComplete = { a -> scope.launch { activityDao.updateActivity(a.copy(status = "Concluída", concluidoEm = LocalDateTime.now())) } },
                        onDelete = { a -> scope.launch { activityDao.deleteActivity(a) } }
                    )
                }
                composable("andamento") {
                    InProgressScreen(
                        activities = activities.filter { it.status == "Em andamento" },
                        onComplete = { a -> scope.launch { activityDao.updateActivity(a.copy(status = "Concluída", concluidoEm = LocalDateTime.now())) } },
                        onChangeStatus = { activity, newStatus ->
                            if (newStatus == "Pendente") {
                                scope.launch { activityDao.updateActivity(activity.copy(status = "Pendente")) }
                            }
                        },
                        onDelete = { a -> scope.launch { activityDao.deleteActivity(a) } }

                    )
                }

                composable("concluidas") {
                    CompletedScreen(
                        activities = activities.filter { it.status == "Concluída" },
                        onChangeStatus = { activity, newStatus ->
                            scope.launch {
                                val updatedActivity = when (newStatus) {
                                    "Pendente" -> activity.copy(status = "Pendente", concluidoEm = null)
                                    "Em andamento" -> activity.copy(status = "Em andamento", concluidoEm = null)
                                    else -> activity
                                }
                                activityDao.updateActivity(updatedActivity)
                            }
                        },
                        onDelete = { a -> scope.launch { activityDao.deleteActivity(a) } }
                    )
                }
            }

            if (showAddDialog) {
                AddActivityDialog(
                    onAdd = { newEntity ->
                        scope.launch { activityDao.insertActivity(newEntity) }
                        showAddDialog = false
                    },
                    onDismiss = { showAddDialog = false }
                )
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController, items: List<BottomNavItem>) {
    NavigationBar(containerColor = Color(0xFFE6D6FF)) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = { navController.navigate(item.route) },
                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFF3B0066),
                    selectedTextColor = Color(0xFF3B0066),
                    indicatorColor = Color(0xFFD1B2FF)
                )
            )
        }
    }
}
