package com.example.smartcompra.view.comparador.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.smartcompra.data.models.Producto
import com.example.smartcompra.ui.theme.AppShape

@Composable
fun ProductoCard (
    producto: Producto
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
            Modifier.fillMaxSize()
        ) {
            Text(
                text = producto.nombre
            )
            Row {
                Column {
                    Text("Precio: ")
                    Text("Cantidad: ")
                    Text("Precio unitario: ")
                }
                Column {
                    Text(producto.precio.toString())
                    Text(producto.cantidad.toString())
                    Text(producto.precioNormalizado.toString())
                }
            }
        }
    }
}
