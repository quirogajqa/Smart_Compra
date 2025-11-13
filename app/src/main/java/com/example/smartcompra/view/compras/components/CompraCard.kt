package com.example.smartcompra.view.compras.components

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
import com.example.smartcompra.data.models.Compra
import com.example.smartcompra.ui.theme.AppShape
import com.example.smartcompra.utils.toChileanPesos
import com.example.smartcompra.viewmodel.ComprasViewModel

@Composable
fun CompraCard (
    compra: Compra,
    viewModel: ComprasViewModel = hiltViewModel()
){
    Card(
    modifier = Modifier
    .padding(vertical = 4.dp),
    shape = AppShape.medium,
    colors = CardDefaults.cardColors(
    containerColor = MaterialTheme.colorScheme.primary,
    contentColor = MaterialTheme.colorScheme.tertiary
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
                        text = compra.nombre,
                        style = MaterialTheme.typography.titleLarge,
                    )
                }
            }

            Spacer(Modifier.height(8.dp))

            Row(
                Modifier
                    .fillMaxSize(),
            ) {
                Row (
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Column {
                        Text("Precio: ", fontWeight = FontWeight.Bold)
                        Text("Cantidad: ", fontWeight = FontWeight.Bold)
                        if (compra.descuento > 1) Text(
                            "Descuento: ",
                            fontWeight = FontWeight.Bold
                        )
                        if (compra.pack > 1) Text("Pack: ", fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(8.dp))
                        Text(
                            "Precio final: ",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 20.sp
                        )
                    }

                    Spacer(Modifier.width(12.dp))

                    Column(
                        Modifier
                            .width(100.dp)
                    ) {
                        Text(
                            compra.precio.toChileanPesos(),
                            Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Right
                        )

                        Text(
                            "${compra.cantidad} un",
                            Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Right
                        )

                        if (compra.descuento > 1) {
                            Text(
                                "${compra.descuento} %",
                                Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Right
                            )
                        }

                        if (compra.pack > 1) {
                            Text(
                                "${compra.pack} un",
                                Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Right
                            )
                        }
                        Spacer(Modifier.height(8.dp))
                        Text(
                            compra.precioFinal.toChileanPesos(),
                            Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Right,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 20.sp
                        )

                    }
                }
                Spacer(Modifier.width(2.dp))
                IconButton(
                    onClick = { viewModel.onCompraDeleted(compra) },
                    Modifier
                        .align(Alignment.CenterVertically),
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