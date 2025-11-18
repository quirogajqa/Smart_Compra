package com.example.smartcompra.view.home.components

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
fun ListaCompraCard (
    listaCompra: ListaCompra,
    viewModel: HomeViewModel = hiltViewModel()
){
    Card(
        modifier = Modifier
            .padding(vertical = 4.dp),
        shape = AppShape.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        )
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 16.dp)
        ) {

            Row(
                Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = listaCompra.nombre,
                        style = MaterialTheme.typography.titleLarge,
                    )
                }
            }

            Spacer(Modifier.height(8.dp))

            Row(
                Modifier
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row (
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Column {

//                        Text("Total de productos: ", fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(8.dp))
                        Text(
                            "Total: ",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }

                    Spacer(Modifier.width(12.dp))

                    Column(
                        Modifier
                            .width(130.dp)
                    ) {
//                        Text(
//                            "${listaCompra.} un",
//                            Modifier.fillMaxWidth(),
//                            textAlign = TextAlign.Right
//                        )

                        Spacer(Modifier.height(8.dp))
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
                Spacer(Modifier.width(12.dp))
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
        }
    }
}