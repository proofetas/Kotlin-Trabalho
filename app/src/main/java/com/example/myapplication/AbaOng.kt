package com.example.myapplication

// ╔══════════════════════════════════════════════════════════╗
// ║  AbaOng.kt — "CCA Datão" + campanhas + lista de ONGs     ║
// ║  LISTA 2 (data class Ong): LazyColumn + Card             ║
// ║  ADICIONAR → botão "+" (formulário)                      ║
// ║  REMOVER   → ícone de lixeira OU clique longo            ║
// ║  CLICAR    → abre os Detalhes da ONG                     ║
// ╚══════════════════════════════════════════════════════════╝

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AbaOng(viewModel: MeuViewModel, onOngClick: (Int) -> Unit) {

    val context = LocalContext.current

    // UMA LazyColumn só com tudo: cabeçalho, campanhas, título e ONGs.
    // item { } = um elemento avulso | items(lista) { } = um por elemento da lista
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Cores.Fundo),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 88.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        // Cabeçalho "CCA Datão 🐾"
        item {
            Text(
                text = "CCA Datão 🐾",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Cores.Texto
            )
        }

        // Campanhas de adoção
        items(viewModel.campanhas) { campanha ->
            ItemCampanha(campanha)
        }

        // Linha divisória + título da seção
        item {
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                color = Cores.Teal.copy(alpha = 0.4f)
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .width(4.dp)
                        .height(20.dp)
                        .background(Cores.Teal)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Organização Não Governamental (ONG)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Cores.Texto
                )
            }
        }

        // A LISTA de ONGs (data class Ong)
        items(viewModel.ongs, key = { "ong${it.id}" }) { ong ->
            ItemOng(
                ong = ong,
                onClick = { onOngClick(ong.id) },
                removerItem = {
                    Toast.makeText(context, "Removendo ${ong.nome}", Toast.LENGTH_SHORT).show()
                    viewModel.removerOng(ong.id)
                }
            )
        }
    }
}

@Composable
private fun ItemCampanha(campanha: Campanha) {

    // Estado LOCAL do card: aberto ("Saiba mais") ou fechado
    var expandido by remember { mutableStateOf(false) }

    Row(verticalAlignment = Alignment.CenterVertically) {

        Card(
            modifier = Modifier
                .weight(1f)
                .clickable { expandido = !expandido },
            shape = RoundedCornerShape(18.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Cores.Laranja),
                    contentAlignment = Alignment.Center
                ) {
                    Text("🐾", fontSize = 20.sp)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(campanha.titulo, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = Cores.Texto)
                    Text(
                        text = if (expandido) "Mostrar menos" else "Saiba mais",
                        color = Cores.Teal,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    if (expandido) {
                        Text(campanha.descricao, fontSize = 12.sp, color = Cores.Cinza)
                    }
                }
            }
        }

        // Bolinha com a data (só aparece se a campanha tem data)
        if (campanha.data != null) {
            Spacer(modifier = Modifier.width(8.dp))
            val corBorda = if (campanha.confirmada) Cores.Teal else Cores.Cinza.copy(alpha = 0.5f)
            Column(
                modifier = Modifier
                    .size(58.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .border(BorderStroke(2.dp, corBorda), CircleShape),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (campanha.confirmada) {
                    Icon(Icons.Default.Check, contentDescription = "Confirmada", tint = Cores.Teal, modifier = Modifier.size(11.dp))
                }
                Text(campanha.data, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Cores.Texto)
                Text(campanha.hora ?: "", fontSize = 9.sp, color = Cores.Cinza)
            }
        }
    }
}

@Composable
private fun ItemOng(ong: Ong, onClick: () -> Unit, removerItem: () -> Unit) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = onClick,
                onLongClick = { removerItem() }
            ),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Cores.TealClaro),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Home, contentDescription = null, tint = Cores.Teal)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = ong.nome,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp,
                color = Cores.Texto,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = { removerItem() }) {
                Icon(Icons.Default.Delete, contentDescription = "Remover ${ong.nome}", tint = Cores.Cinza)
            }
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = Cores.Cinza)
        }
    }
}
