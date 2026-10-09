package com.example.myapplication

// AbaPets.kt — "LOUCOS PARA TE CONHECER"
// Grade de 2 colunas (LazyVerticalGrid).
// O selo (check) no canto da foto marca/desmarca o pet como FAVORITO.
// Clicar no card abre os detalhes.

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AbaPets(viewModel: MeuViewModel, onPetClick: (Int) -> Unit) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .background(Cores.Fundo),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(viewModel.pets, key = { it.id }) { pet ->

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onPetClick(pet.id) },
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {

                Box {
                    FotoPet(
                        pet = pet,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(115.dp)
                    )

                    // Selo de favorito (canto superior direito da foto)
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(if (pet.favorito) Cores.Teal else Color.White.copy(alpha = 0.8f))
                            .clickable { viewModel.alternarFavorito(pet.id) },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Favoritar ${pet.nome}",
                            tint = if (pet.favorito) Color.White else Cores.Cinza,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }

                Column(modifier = Modifier.padding(12.dp)) {
                    Text(pet.nome.uppercase(), fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Cores.Texto)
                    Text(pet.raca, fontSize = 11.sp, color = Cores.Cinza)
                    Text(pet.idadeTexto, fontSize = 11.sp, color = Cores.Cinza)
                }
            }
        }
    }
}
