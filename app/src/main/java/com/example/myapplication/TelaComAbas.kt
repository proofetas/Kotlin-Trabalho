package com.example.myapplication


import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TelaComAbas(navController: NavHostController, viewModel: MeuViewModel) {

    // NavController INTERNO — só para navegar entre as abas
    val navInterno = rememberNavController()

    // Qual aba está aberta agora? (usado para trocar título e botão flutuante)
    val backStackEntry by navInterno.currentBackStackEntryAsState()
    val rotaAtual = backStackEntry?.destination?.route

    val titulo = when (rotaAtual) {
        RotasAbas.ABA_PETS -> "Loucos para te conhecer"
        RotasAbas.ABA_ONG -> "ONGs"
        RotasAbas.ABA_ENCONTRO -> "Encontro"
        RotasAbas.ABA_PERFIL -> "Perfil"
        else -> "Adote seu Amigo"
    }

    Scaffold(
        containerColor = Cores.Fundo,
        topBar = {
            TopAppBar(
                title = { Text(titulo) },
                actions = {
                    // Só na aba PETS: contador de favoritos (coração + número)
                    if (rotaAtual == RotasAbas.ABA_PETS) {
                        Row {
                            Icon(Icons.Default.Favorite, contentDescription = "Favoritos")
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("${viewModel.pets.count { it.favorito }}")
                            Spacer(modifier = Modifier.width(16.dp))
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Cores.Teal,
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        },
        bottomBar = {
            // Barra de navegação inferior — usa o navController INTERNO
            BottomNavBar(navInterno = navInterno,)
        },
        floatingActionButton = {
            // O botão "+" muda conforme a aba (usa o navController PRINCIPAL)
            when (rotaAtual) {
                RotasAbas.ABA_HOME -> FloatingActionButton(
                    onClick = { navController.navigate(Rotas.FORM_PET) },
                    containerColor = Cores.Teal,
                    contentColor = Color.White
                ) { Icon(Icons.Default.Add, contentDescription = "Adicionar pet") }

                RotasAbas.ABA_ONG -> FloatingActionButton(
                    onClick = { navController.navigate(Rotas.FORM_ONG) },
                    containerColor = Cores.Teal,
                    contentColor = Color.White
                ) { Icon(Icons.Default.Add, contentDescription = "Adicionar ONG") }
            }
        }
    ) { padding ->

        // NavHost INTERNO — controla qual aba está ativa
        NavHost(
            navController = navInterno,
            startDestination = RotasAbas.ABA_HOME,
            modifier = Modifier.padding(padding)
        ) {
            composable(RotasAbas.ABA_HOME) {
                AbaHome(
                    viewModel = viewModel,
                    onPetClick = { petId -> navController.navigate(Rotas.petDetalhes(petId)) }
                )
            }
            composable(RotasAbas.ABA_PETS) {
                AbaPets(
                    viewModel = viewModel,
                    onPetClick = { petId -> navController.navigate(Rotas.petDetalhes(petId)) }
                )
            }
            composable(RotasAbas.ABA_ONG) {
                AbaOng(
                    viewModel = viewModel,
                    onOngClick = { ongId -> navController.navigate(Rotas.ongDetalhes(ongId)) }
                )
            }
            composable(RotasAbas.ABA_ENCONTRO) {
                AbaEncontro(
                    viewModel = viewModel,
                    onPetClick = { petId -> navController.navigate(Rotas.petDetalhes(petId)) }
                )
            }
            composable(RotasAbas.ABA_PERFIL) {
                AbaPerfil(
                    viewModel = viewModel,
                    onCadastrarPet = { navController.navigate(Rotas.FORM_PET) },
                    onCadastrarOng = { navController.navigate(Rotas.FORM_ONG) }
                )
            }
        }
    }
}

// ── Preview ──────────────────────────────────────
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TelaComAbasPreview() {
    MaterialTheme {
        TelaComAbas(
            navController = rememberNavController(),
            viewModel = viewModel()
        )
    }
}
