package com.example.smartcompra.view.comparador.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.smartcompra.data.models.Producto
import com.example.smartcompra.ui.theme.AppShape
import com.example.smartcompra.utils.toChileanPesos
import com.example.smartcompra.viewmodel.ComparadorViewModel

@Composable
fun ProductoCard (
    producto: Producto,
    viewModel: ComparadorViewModel = hiltViewModel()
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

            Row (Modifier
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                Column {
                    Text(
                        text = producto.nombre,
                        style = MaterialTheme.typography.titleLarge,
                    )
                    if (!producto.marca.isNullOrEmpty()) {
                        Text(
                            text = producto.marca,
                        )
                    }
                }
                if (producto.bestPrice) {
                    Card(
                        shape = AppShape.medium,
                        colors = CardDefaults.cardColors(
                            containerColor = Color.Green,
                            contentColor = MaterialTheme.colorScheme.tertiary
                        )
                    ) {
                        Box(
                            Modifier.padding(horizontal = 10.dp, vertical = 2.dp)
                        ) {
                            Text("MEJOR")
                        }
                    }
                }
            }

            Spacer(Modifier.height(8.dp))

            Row (Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row {
                    Column {
                        Text("Precio: ", fontWeight = FontWeight.Bold)
                        Text("Cantidad: ", fontWeight = FontWeight.Bold)
                        if (producto.descuento > 1) Text("Descuento: ", fontWeight = FontWeight.Bold)
                        if (producto.pack > 1) Text("Pack: ", fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(8.dp))
                        Text("Precio\nunitario: ", fontWeight = FontWeight.ExtraBold, fontSize = 20.sp )
                    }

                    Spacer(Modifier.width(12.dp))

                    Column(
                        Modifier
                            .width(130.dp)
                    ) {
                        Text(producto.precio.toChileanPesos(),
                            Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Right )

                        Text("${producto.cantidad} ${producto.unidad}",
                            Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Right)

                        if (producto.descuento > 1) {
                            Text(
                                "${producto.descuento} %",
                                Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Right
                            )
                        }

                        if (producto.pack > 1) {
                            Text(
                                "${producto.pack} un",
                                Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Right
                            )
                        }

                        Spacer(Modifier.height(8.dp))

                        Text(producto.precioNormalizado.toChileanPesos(),
                            Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Right,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 20.sp
                        )
                        Text("por ${producto.unidadNormalizada}",
                            Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Right,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }

                IconButton(
                    onClick = { viewModel.onProductoDeleted( producto ) }
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
