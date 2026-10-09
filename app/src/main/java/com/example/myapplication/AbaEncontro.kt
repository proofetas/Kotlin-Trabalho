package com.example.myapplication


import androidx.compose.foundation.background
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AbaEncontro(viewModel: MeuViewModel, onPetClick: (Int) -> Unit) {

    val favoritos = viewModel.pets.filter { it.favorito }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Cores.Fundo)
    ) {

        if (favoritos.isEmpty()) {
            Text(
                text = "Você ainda não marcou nenhum pet. Toque no check de um pet na aba PETS para ele aparecer aqui.",
                color = Cores.Cinza,
                modifier = Modifier.padding(24.dp)
            )
        }

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(favoritos, key = { it.id }) { pet ->

                val agendado = viewModel.encontros.contains(pet.id)

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onPetClick(pet.id) },
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {

                        FotoPet(pet = pet, modifier = Modifier.size(90.dp))

                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(12.dp)
                        ) {
                            Text(pet.nome, fontWeight = FontWeight.Bold, color = Cores.Texto)
                            Text(
                                text = viewModel.buscarOng(pet.ongId)?.nome ?: "",
                                fontSize = 12.sp,
                                color = Cores.Cinza
                            )
                            if (agendado) {
                                Text(
                                    text = "Encontro agendado ✓",
                                    fontSize = 12.sp,
                                    color = Cores.Teal,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }

                        if (agendado) {
                            OutlinedButton(
                                onClick = { viewModel.alternarEncontro(pet.id) },
                                modifier = Modifier.padding(end = 8.dp)
                            ) { Text("Cancelar", fontSize = 12.sp) }
                        } else {
                            Button(
                                onClick = { viewModel.alternarEncontro(pet.id) },
                                colors = ButtonDefaults.buttonColors(containerColor = Cores.Teal),
                                modifier = Modifier.padding(end = 8.dp)
                            ) { Text("Agendar", fontSize = 12.sp) }
                        }
                    }
                }
            }
        }
    }
}
