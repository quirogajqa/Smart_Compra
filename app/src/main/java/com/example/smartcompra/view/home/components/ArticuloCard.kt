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
import com.example.smartcompra.data.models.ArticuloToSave
import com.example.smartcompra.ui.theme.AppShape
import com.example.smartcompra.utils.toChileanPesos
import com.example.smartcompra.viewmodel.ComprasViewModel


@Composable
fun ArticuloCard(
    articuloComprado: ArticuloToSave
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
                        text = articuloComprado.nombre,
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
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Precio: ", fontWeight = FontWeight.Bold)
                        Text("Cantidad: ", fontWeight = FontWeight.Bold)
                        if (articuloComprado.descuento > 1) Text(
                            "Descuento: ",
                            fontWeight = FontWeight.Bold
                        )
                        if (articuloComprado.pack > 1) Text("Pack: ", fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(8.dp))
                        Text(
                            "Precio final: ",
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
                        Text(
                            articuloComprado.precio.toChileanPesos(),
                            Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Right
                        )

                        Text(
                            "${articuloComprado.cantidad} un",
                            Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Right
                        )

                        if (articuloComprado.descuento > 1) {
                            Text(
                                "${articuloComprado.descuento} %",
                                Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Right,
                                color = MaterialTheme.colorScheme.tertiary
                            )
                        }

                        if (articuloComprado.pack > 1) {
                            Text(
                                "${articuloComprado.pack} un",
                                Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Right
                            )
                        }
                        Spacer(Modifier.height(8.dp))
                        Text(
                            articuloComprado.precioFinal.toChileanPesos(),
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
    }
}