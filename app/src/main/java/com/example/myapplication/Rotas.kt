package com.example.myapplication

// Rotas.kt — constantes das rotas (evita erros de digitação)
// Dois objetos, porque temos DOIS NavHosts (igual à aula):
//   Rotas      → NavHost PRINCIPAL (AppNavigation)
//   RotasAbas  → NavHost INTERNO das abas (TelaComAbas)

object Rotas {
    const val HOME = "home"                       // a tela que contém as abas
    const val PET_DETALHES = "pet/{petId}"
    const val ONG_DETALHES = "ong/{ongId}"
    const val FORM_PET = "form_pet"
    const val FORM_ONG = "form_ong"

    // Funções que montam a rota com o argumento já preenchido
    fun petDetalhes(id: Int) = "pet/$id"
    fun ongDetalhes(id: Int) = "ong/$id"
}

object RotasAbas {
    const val ABA_HOME = "aba_home"
    const val ABA_PETS = "aba_pets"
    const val ABA_ONG = "aba_ong"
    const val ABA_ENCONTRO = "aba_encontro"
    const val ABA_PERFIL = "aba_perfil"
}
