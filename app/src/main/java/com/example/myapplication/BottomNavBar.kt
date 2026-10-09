package com.example.myapplication

// BottomNavBar.kt — barra de navegação inferior
// Usa o NavController INTERNO (o das abas), não o principal.

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

// Pequena classe só para montar os 5 itens com um loop (em vez de copiar 5x)
private data class ItemBarra(val rota: String, val rotulo: String, val icone: ImageVector)

@Composable
fun BottomNavBar(navInterno: NavHostController, popUpTo: (Any, () -> Unit?) -> Unit) {

    // Observa a pilha de navegação para saber qual aba está ativa
    val backStackEntry by navInterno.currentBackStackEntryAsState()
    val rotaAtual = backStackEntry?.destination?.route

    val itens = listOf(
        ItemBarra(RotasAbas.ABA_HOME, "HOME", Icons.Default.Home),
        // PETS usa o coração; ENCONTRO o coração vazio
        ItemBarra(RotasAbas.ABA_PETS, "PETS", Icons.Default.Favorite),
        ItemBarra(RotasAbas.ABA_ONG, "ONG", Icons.Default.Search),
        ItemBarra(RotasAbas.ABA_ENCONTRO, "ENCONTRO", Icons.Default.FavoriteBorder),
        ItemBarra(RotasAbas.ABA_PERFIL, "PERFIL", Icons.Default.Person)
    )

    NavigationBar(containerColor = Color.White) {
        itens.forEach { item ->
            NavigationBarItem(
                selected = rotaAtual == item.rota,
                onClick = {
                    navInterno.navigate(item.rota) {
                        // volta até a aba inicial e guarda o estado (não empilha abas)
                        popUpTo(navInterno.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true   // clicar na aba atual não duplica
                        restoreState = true      // volta com o scroll/estado de antes
                    }
                },
                icon = { Icon(imageVector = item.icone, contentDescription = item.rotulo) },
                label = { Text(item.rotulo, fontSize = 10.sp, fontWeight = FontWeight.Bold) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Cores.Teal,
                    selectedTextColor = Cores.Teal,
                    unselectedIconColor = Cores.Cinza,
                    unselectedTextColor = Cores.Cinza,
                    indicatorColor = Cores.TealClaro
                )
            )
        }
    }
}
