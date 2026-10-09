package com.example.myapplication

// AppNavigation.kt — NavHost PRINCIPAL
// Aqui ficam as rotas de TODAS as telas "grandes" do app.
// As 5 abas (Home, Pets, ONG, Encontro, Perfil) ficam dentro da TelaComAbas,
// que tem o seu próprio NavHost interno (veja TelaComAbas.kt).

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun AppNavigation() {
    // 1. Cria o controlador de navegação (pilha de telas)
    val navController = rememberNavController()

    // 2. UM ViewModel só, criado aqui e passado para todas as telas.
    //    É por isso que adicionar um pet na tela de formulário aparece na lista.
    val viewModel: MeuViewModel = viewModel()

    // 3. Mapa de rotas do app
    NavHost(
        navController = navController,
        startDestination = Rotas.HOME
    ) {
        // Rota "home" → tela com TopBar + BottomNavigation + abas
        composable(Rotas.HOME) {
            TelaComAbas(navController = navController, viewModel = viewModel)
        }

        // Rota "pet/{petId}" → recebe o ID do pet pela rota (Int)
        composable(
            route = Rotas.PET_DETALHES,
            arguments = listOf(navArgument("petId") { type = NavType.IntType })
        ) { backStackEntry ->
            val petId = backStackEntry.arguments?.getInt("petId") ?: -1
            TelaDetalhesPet(
                petId = petId,
                viewModel = viewModel,
                onVoltar = { navController.popBackStack() },
                onAbrirOng = { ongId -> navController.navigate(Rotas.ongDetalhes(ongId)) }
            )
        }

        // Rota "ong/{ongId}" → idem, para ONG
        composable(
            route = Rotas.ONG_DETALHES,
            arguments = listOf(navArgument("ongId") { type = NavType.IntType })
        ) { backStackEntry ->
            val ongId = backStackEntry.arguments?.getInt("ongId") ?: -1
            TelaDetalhesOng(
                ongId = ongId,
                viewModel = viewModel,
                onVoltar = { navController.popBackStack() },
                onAbrirPet = { petId -> navController.navigate(Rotas.petDetalhes(petId)) }
            )
        }

        composable(Rotas.FORM_PET) {
            TelaFormPet(viewModel = viewModel, onVoltar = { navController.popBackStack() })
        }

        composable(Rotas.FORM_ONG) {
            TelaFormOng(viewModel = viewModel, onVoltar = { navController.popBackStack() })
        }
    }
}
