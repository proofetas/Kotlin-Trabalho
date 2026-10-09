package com.example.myapplication

// MeuViewModel.kt
// ViewModel = guarda o ESTADO do app e sobrevive à rotação de tela.
// Aqui ficam as DUAS LISTAS (pets e ongs). Como o mesmo ViewModel é passado
// para todas as telas, adicionar/remover em uma tela aparece em todas.
//
// Padrão usado (igual ao contador da aula):
//   _pets  → lista mutável PRIVADA (só o ViewModel mexe)
//   pets   → versão só-leitura que as telas enxergam
//   funções públicas → as telas chamam essas funções para alterar

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class MeuViewModel : ViewModel() {

    // ── ONGs ────────────────────────────────────────────────
    private val _ongs = mutableStateListOf(
        Ong(1, "Patinhas do Sul", "Curitiba / PR", "(41) 99999-0001", "ONG que resgata e reabilita cães abandonados em Curitiba e região."),
        Ong(2, "Amigo Fiel ONG", "Irati / PR", "(42) 99999-0002", "Atua no resgate de animais em situação de risco no interior do Paraná."),
        Ong(3, "Patinhas Felizes", "Curitiba / PR", "(41) 99999-0003", "Foco em filhotes e adoção responsável."),
        Ong(4, "Instituto Amigo Animal", "São José dos Pinhais / PR", "(41) 99999-0004", "Castração gratuita e feiras de adoção."),
        Ong(5, "Lar dos Focinhos", "Pinhais / PR", "(41) 99999-0005", "Lar temporário para cães idosos e com necessidades especiais."),
        Ong(6, "Resgate do Sul", "Araucária / PR", "(41) 99999-0006", "Resgate de animais vítimas de maus-tratos."),
        Ong(7, "Fundação Pets Amigos", "Colombo / PR", "(41) 99999-0007", "Mantém abrigo e programa de apadrinhamento.")
    )
    val ongs: List<Ong> get() = _ongs

    // ── PETS ────────────────────────────────────────────────
    private val _pets = mutableStateListOf(
        Pet(1, "Ziggy", "Vira-Lata", 2, "Ziggy é um filhote cheio de energia e muito curioso.", 3),
        Pet(2, "Chico", "Jack Russell mix", 4, "Chico é esperto, adora correr e brincar de bolinha.", 3),
        Pet(3, "Toby", "Linguicinha", 2, "Toby é calmo, carinhoso e se dá bem com outros pets.", 4),
        Pet(4, "Bob", "Vira-Lata", 1, "Bob foi resgatado ainda bebê e precisa de uma família atenciosa.", 6),
        Pet(5, "Lola", "Poodle", 8, "Lola é meiga, sociável e adora colo.", 4),
        Pet(6, "Bidu", "Beagle mix", 12, "Bidu é farejador nato e muito brincalhão.", 7),
        Pet(7, "Max", "Corgi", 72, "Max foi abandonado pelo antigo tutor e está atualmente com 6 anos de idade. Ele é dócil, brincalhão e adora crianças.", 1, vacinado = true),
        Pet(8, "Thor", "Golden Retriever", 48, "Thor foi encontrado na rua em situação de risco e está atualmente com 4 anos de idade. Muito carinhoso e obediente, ótimo para famílias.", 2, vacinado = true)
    )
    val pets: List<Pet> get() = _pets

    // ── ENCONTROS (ids dos pets com visita agendada) ────────
    private val _encontros = mutableStateListOf<Int>()
    val encontros: List<Int> get() = _encontros

    // ── CAMPANHAS (fixas, só para a tela de ONG) ────────────
    val campanhas = listOf(
        Campanha("Campanha de adoção", "Feira de adoção no Parque Barigui, com pets de várias ONGs.", "08/08", "08hrs", true),
        Campanha("Campanha de adoção", "Mutirão de adoção e vacinação no Shopping Palladium.", "10/10", "08hrs", true),
        Campanha("Campanha de adoção", "Data ainda a definir. Fique de olho!"),
        Campanha("Campanha de adoção", "Feira de adoção na Praça Osório. Aguardando confirmação.", "11/08", "08hrs", false)
    )

    // Próximos ids para itens novos
    private var proximoPetId = 9
    private var proximaOngId = 8

    // ── Funções de BUSCA (o que a tela de Detalhes usa) ─────
    fun buscarPet(id: Int): Pet? = _pets.find { it.id == id }
    fun buscarOng(id: Int): Ong? = _ongs.find { it.id == id }
    fun petsDaOng(ongId: Int): List<Pet> = _pets.filter { it.ongId == ongId }

    // ── Funções que ALTERAM o estado (as telas chamam essas) ─
    fun adicionarPet(nome: String, raca: String, idadeMeses: Int, descricao: String, ongId: Int, vacinado: Boolean) {
        _pets.add(0, Pet(proximoPetId, nome, raca, idadeMeses, descricao, ongId, vacinado))
        proximoPetId++
    }

    fun removerPet(id: Int) {
        _pets.removeAll { it.id == id }
        _encontros.removeAll { it == id }
    }

    fun alternarFavorito(id: Int) {
        val posicao = _pets.indexOfFirst { it.id == id }
        if (posicao >= 0) {
            // data class é imutável → criamos uma CÓPIA com o favorito invertido
            _pets[posicao] = _pets[posicao].copy(favorito = !_pets[posicao].favorito)
        }
    }

    fun alternarEncontro(id: Int) {
        if (_encontros.contains(id)) {
            _encontros.removeAll { it == id }
        } else {
            _encontros.add(id)
        }
    }

    fun adicionarOng(nome: String, cidade: String, telefone: String, descricao: String) {
        _ongs.add(0, Ong(proximaOngId, nome, cidade, telefone, descricao))
        proximaOngId++
    }

    fun removerOng(id: Int) {
        // apagar uma ONG apaga também os pets dela
        petsDaOng(id).forEach { removerPet(it.id) }
        _ongs.removeAll { it.id == id }
    }
}
