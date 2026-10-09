package com.example.myapplication

// ╔══════════════════════════════════════════════════════════╗
// ║  TelaDetalhesPet.kt — DETALHES 1                         ║
// ║  Recebe só o ID pela rota e busca o pet no ViewModel.    ║
// ║  Vai além de mostrar os campos (seção 3.2 do trabalho):  ║
// ║   1) FASE DA VIDA calculada pela idade (Filhote/Adulto…) ║
// ║   2) Cartão da ONG responsável (dados da OUTRA lista)    ║
// ║      → clicar abre a tela de Detalhes da ONG             ║
// ║   3) Botão que ALTERA o item (favoritar)                 ║
// ╚══════════════════════════════════════════════════════════╝

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun TelaDetalhesPet(
    petId: Int,
    viewModel: MeuViewModel,
    onVoltar: () -> Unit,
    onAbrirOng: (Int) -> Unit
) {
    // Busca o pet CERTO pelo id que veio na rota (pode ser null se foi removido)
    val pet = viewModel.buscarPet(petId)

    Scaffold(
        containerColor = Cores.Fundo,
        topBar = { BarraTopo(titulo = pet?.nome ?: "Pet", onVoltar = onVoltar) }
    ) { padding ->

        if (pet == null) {
            Text(
                text = "Pet não encontrado.",
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
            )
        } else {

            val ong = viewModel.buscarOng(pet.ongId)

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                FotoPet(
                    pet = pet,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp)
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(pet.nome, fontWeight = FontWeight.Bold, fontSize = 24.sp, color = Cores.Texto)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(pet.raca, color = Cores.Teal, fontWeight = FontWeight.SemiBold)
                }

                // Etiquetas: idade (texto), fase (CALCULADA) e vacina
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Etiqueta(pet.idadeTexto)
                    Etiqueta(pet.fase)
                    Etiqueta(if (pet.vacinado) "Vacinado" else "Não vacinado")
                }

                Text(pet.descricao, color = Cores.Cinza, lineHeight = 20.sp)

                // Cartão da ONG responsável → navega para os detalhes da ONG
                if (ong != null) {
                    Card(
                        onClick = { onAbrirOng(ong.id) },
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text("ONG responsável", fontSize = 11.sp, color = Cores.Cinza)
                                Text(ong.nome, fontWeight = FontWeight.Bold, color = Cores.Texto)
                                Text(ong.cidade, fontSize = 12.sp, color = Cores.Cinza)
                            }
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                                contentDescription = "Ver ONG",
                                tint = Cores.Cinza
                            )
                        }
                    }
                }

                // Botão que altera o próprio item (favorito)
                Button(
                    onClick = { viewModel.alternarFavorito(pet.id) },
                    colors = ButtonDefaults.buttonColors(containerColor = Cores.Teal),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = if (pet.favorito) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(if (pet.favorito) "Nos meus favoritos" else "Quero conhecer ${pet.nome}")
                }
            }
        }
    }
}

// ── Preview ──────────────────────────────────────
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TelaDetalhesPetPreview() {
    MaterialTheme {
        TelaDetalhesPet(
            petId = 1,
            viewModel = viewModel(),
            onVoltar = {},
            onAbrirOng = {}
        )
    }
}
