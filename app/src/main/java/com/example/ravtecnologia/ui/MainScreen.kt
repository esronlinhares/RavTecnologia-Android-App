package com.example.ravtecnologia.ui // Pacote da parte de interface (UI)

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.* // Ícones prontos do Material Design
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.* // Navegação entre telas com Compose
import com.example.ravtecnologia.data.database.AppDatabase
import com.example.ravtecnologia.data.entity.ActivityEntity
import com.example.ravtecnologia.ui.list.*
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import androidx.navigation.NavHostController

// Classe para representar cada item do menu inferior
data class BottomNavItem(val route: String, val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val context = LocalContext.current // Contexto atual da aplicação
    val db = AppDatabase.getDatabase(context) // Instância do banco de dados
    val activityDao = db.activityDao() // Acesso ao DAO (operações no banco)
    val scope = rememberCoroutineScope() // Cria uma corrotina para operações assíncronas

    var showAddDialog by remember { mutableStateOf(false) } // Controla se o diálogo de adicionar está aberto
    val navController = rememberNavController() // Controla a navegação entre telas

    // Observa as atividades no banco em tempo real
    val activities by activityDao.getAllActivitiesFlow().collectAsState(initial = emptyList())

    // Itens da barra inferior (abas)
    val bottomItems = listOf(
        BottomNavItem("pendentes", "Pendentes", Icons.Default.Pending),
        BottomNavItem("andamento", "Em andamento", Icons.Default.HourglassBottom),
        BottomNavItem("concluidas", "Concluídas", Icons.Default.CheckCircle)
    )

    // Estrutura principal da tela (scaffold = layout base com topbar, bottom e FAB)
    Scaffold(
        topBar = { TopAppBar(title = { Text("Minhas Atividades") }) }, // Barra superior com título
        floatingActionButton = { // Botão flutuante para adicionar nova atividade
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(Icons.Default.Add, contentDescription = null)
            }
        },
        bottomBar = { BottomNavigationBar(navController, bottomItems) } // Barra inferior com abas
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) { // Conteúdo principal com padding do Scaffold

            // Controle de navegação entre as três telas
            NavHost(navController, startDestination = "pendentes") {

                // Tela de atividades pendentes
                composable("pendentes") {
                    PendingScreen(
                        activities = activities.filter { it.status == "Pendente" },
                        onStart = { a -> scope.launch { activityDao.updateActivity(a.copy(status = "Em andamento")) } },
                        onComplete = { a -> scope.launch { activityDao.updateActivity(a.copy(status = "Concluída", concluidoEm = LocalDateTime.now())) } },
                        onDelete = { a -> scope.launch { activityDao.deleteActivity(a) } }
                    )
                }

                // Tela de atividades em andamento
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

                // Tela de atividades concluídas
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

            // Diálogo para adicionar nova atividade
            if (showAddDialog) {
                AddActivityDialog(
                    onAdd = { newEntity ->
                        scope.launch { activityDao.insertActivity(newEntity) } // Insere no banco
                        showAddDialog = false // Fecha o diálogo
                    },
                    onDismiss = { showAddDialog = false } // Fecha ao cancelar
                )
            }
        }
    }
}

// Componente da barra de navegação inferior
@Composable
fun BottomNavigationBar(navController: NavHostController, items: List<BottomNavItem>) {
    NavigationBar(containerColor = Color(0xFFE6D6FF)) { // Fundo lilás
        val navBackStackEntry by navController.currentBackStackEntryAsState() // Observa a tela atual
        val currentRoute = navBackStackEntry?.destination?.route // Rota atual
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route, // Marca o item atual como selecionado
                onClick = { navController.navigate(item.route) }, // Navega para a tela
                icon = { Icon(item.icon, contentDescription = item.label) }, // Ícone do item
                label = { Text(item.label) }, // Texto do item
                colors = NavigationBarItemDefaults.colors( // Cores personalizadas
                    selectedIconColor = Color(0xFF3B0066),
                    selectedTextColor = Color(0xFF3B0066),
                    indicatorColor = Color(0xFFD1B2FF)
                )
            )
        }
    }
}
