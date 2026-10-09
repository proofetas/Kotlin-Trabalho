package com.example.myapplication

// ╔══════════════════════════════════════════════════════════╗
// ║  Modelos.kt — as DATA CLASSES do app                     ║
// ║  data class 1: Ong     data class 2: Pet                 ║
// ║  (Campanha é só um extra para a tela de ONG)             ║
// ╚══════════════════════════════════════════════════════════╝
// data class gera sozinha: equals, toString, copy(), componentN()

data class Ong(
    val id: Int,
    val nome: String,
    val cidade: String,
    val telefone: String,
    val descricao: String
)

data class Pet(
    val id: Int,
    val nome: String,
    val raca: String,
    val idadeMeses: Int,
    val descricao: String,
    val ongId: Int,                 // liga o Pet a uma Ong (relação entre as 2 listas)
    val vacinado: Boolean = false,
    val favorito: Boolean = false,
    val fotoRes: Int? = null        // depois: R.drawable.ziggy (null = sem foto ainda)
) {
    // Propriedades CALCULADAS a partir dos dados (usadas na tela de Detalhes)
    val idadeTexto: String
        get() = when {
            idadeMeses < 12 -> if (idadeMeses == 1) "1 mês" else "$idadeMeses meses"
            idadeMeses / 12 == 1 -> "1 ano"
            else -> "${idadeMeses / 12} anos"
        }

    val fase: String
        get() = when {
            idadeMeses < 12 -> "Filhote"
            idadeMeses < 96 -> "Adulto"
            else -> "Sênior"
        }
}

data class Campanha(
    val titulo: String,
    val descricao: String,
    val data: String? = null,
    val hora: String? = null,
    val confirmada: Boolean = false
)
