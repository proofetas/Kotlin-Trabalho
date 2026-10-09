package com.example.myapplication


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


private val icons: Any

@Composable
fun AbaHome(viewModel: MeuViewModel, onPetClick: (Int) -> Unit) {

    var busca by remember { mutableStateOf("") }
    val context = LocalContext.current

    // A lista exibida é calculada a partir da lista do ViewModel + busca
    val filtrados = viewModel.pets.filter {
        it.nome.contains(busca, ignoreCase = true) ||
                it.raca.contains(busca, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Cores.Fundo)
    ) {

        OutlinedTextField(
            value = busca,
            onValueChange = { busca = it },
            placeholder = { Text("ADOTE SEU AMIGO", fontWeight = FontWeight.Bold) },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar") },
            singleLine = true,
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Cores.Teal
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        LazyColumn(
            contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 88.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(filtrados, key = { it.id }) { pet ->

                val ong = viewModel.buscarOng(pet.ongId)

                ItemPet(
                    pet = pet,
                    local = if (ong != null) "${ong.cidade} — ${ong.nome}" else "",
                    onClick = { onPetClick(pet.id) },
                    removerItem = {
                        Toast.makeText(context, "Removendo ${pet.nome}", Toast.LENGTH_SHORT).show()
                        viewModel.removerPet(pet.id)
                    }
                )
            }
        }
    }
}

@Composable
private fun ItemPet(pet: Pet, local: String, onClick: () -> Unit, removerItem: () -> Unit) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            // clique normal → detalhes | clique LONGO → remove (igual à lista de compras)
            .combinedClickable(
                onClick = onClick,
                onLongClick = { removerItem() }
            ),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {

        FotoPet(
            pet = pet,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
        )

        Column(modifier = Modifier.padding(16.dp)) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = pet.nome.uppercase(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Cores.Texto
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = pet.raca,
                    color = Cores.Teal,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { removerItem() }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Remover ${pet.nome}",
                        tint = Cores.Cinza
                    )
                }
            }

            Text(
                text = "${pet.descricao} Localizado em $local.",
                color = Cores.Cinza,
                fontSize = 12.sp,
                lineHeight = 17.sp
            )
        }
    }
}
