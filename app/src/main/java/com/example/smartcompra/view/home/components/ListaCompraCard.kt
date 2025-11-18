package com.example.smartcompra.view.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.smartcompra.data.models.ArticuloComprado
import com.example.smartcompra.data.models.ListaCompra
import com.example.smartcompra.ui.theme.AppShape
import com.example.smartcompra.utils.toChileanPesos
import com.example.smartcompra.viewmodel.ComprasViewModel
import com.example.smartcompra.viewmodel.HomeViewModel

@Composable
fun ListaCompraCard(
    listaCompra: ListaCompra,
    viewModel: HomeViewModel = hiltViewModel(),
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(vertical = 4.dp),
        shape = AppShape.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = onClick
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {
            Row(
                Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = listaCompra.nombre,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.ExtraBold,
                )

                IconButton(
                    onClick = {
                        viewModel.onListaDeleted(listaCompra)
                    },
                    Modifier
                        .align(Alignment.Top),
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Eliminar"
                    )
                }
            }

            Row(
                Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "11/07/1991"
                )

                Text(
                    text = "Numero de artículos: ${listaCompra.numeroArticulos.toString()}",
                )
            }

            Spacer(Modifier.height(12.dp))

            Row(
                Modifier
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "Total: ",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    listaCompra.total.toChileanPesos(),
                    Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Right,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
        }
    }
}