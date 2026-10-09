package com.example.myapplication

// ╔══════════════════════════════════════════════════════════╗
// ║  TelaDetalhesOng.kt — DETALHES 2                         ║
// ║  Além dos dados da ONG, CRUZA as duas listas:            ║
// ║   - lista os pets que pertencem a ela (filtro por ongId) ║
// ║   - mostra a contagem calculada                          ║
// ║   - clicar num pet abre os detalhes DAQUELE pet          ║
// ║  Também permite remover a ONG (e seus pets).             ║
// ╚══════════════════════════════════════════════════════════╝

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
fun TelaDetalhesOng(
    ongId: Int,
    viewModel: MeuViewModel,
    onVoltar: () -> Unit,
    onAbrirPet: (Int) -> Unit
) {
    val ong = viewModel.buscarOng(ongId)

    Scaffold(
        containerColor = Cores.Fundo,
        topBar = { BarraTopo(titulo = ong?.nome ?: "ONG", onVoltar = onVoltar) }
    ) { padding ->

        if (ong == null) {
            Text(
                text = "ONG não encontrada.",
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
            )
        } else {

            // Pets desta ONG (vem da OUTRA lista, filtrada pelo ongId)
            val pets = viewModel.petsDaOng(ong.id)

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                item {
                    Card(
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text("📍 ${ong.cidade}", color = Cores.Texto)
                            Text("📞 ${ong.telefone}", color = Cores.Texto)
                            Text(ong.descricao, color = Cores.Cinza, fontSize = 13.sp, lineHeight = 18.sp)
                        }
                    }
                }

                item {
                    Text(
                        text = if (pets.size == 1) "1 pet esperando por um lar" else "${pets.size} pets esperando por um lar",
                        fontWeight = FontWeight.Bold,
                        color = Cores.Texto
                    )
                }

                items(pets, key = { it.id }) { pet ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onAbrirPet(pet.id) },
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            FotoPet(pet = pet, modifier = Modifier.size(80.dp))
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(pet.nome, fontWeight = FontWeight.Bold, color = Cores.Texto)
                                Text("${pet.raca} • ${pet.idadeTexto}", fontSize = 12.sp, color = Cores.Cinza)
                            }
                        }
                    }
                }

                item {
                    OutlinedButton(
                        onClick = {
                            viewModel.removerOng(ong.id)
                            onVoltar()
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Remover ONG e seus pets")
                    }
                }
            }
        }
    }
}

// ── Preview ──────────────────────────────────────
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TelaDetalhesOngPreview() {
    MaterialTheme {
        TelaDetalhesOng(
            ongId = 1,
            viewModel = viewModel(),
            onVoltar = {},
            onAbrirPet = {}
        )
    }
}
