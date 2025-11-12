package com.example.smartcompra.view.comparador.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.smartcompra.R
import com.example.smartcompra.ui.theme.AppShape
import com.example.smartcompra.utils.CLPVisualTransformation
import com.example.smartcompra.viewmodel.ComparadorViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgregarProductoScreen(
    viewModel: ComparadorViewModel = hiltViewModel()
) {
    val productoUiState by viewModel.productoUiState.collectAsStateWithLifecycle()

    // 💡 Estado local para controlar si el menú desplegable está abierto
    var expanded by remember { mutableStateOf(false) }

    // 💡 Lista de opciones de unidad
    val unidades = listOf("un", "m", "g", "Kg",  "mL", "L")

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = productoUiState.nombre,
            onValueChange = { viewModel.onNombreChanged(it) },
            shape = AppShape.medium,
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            label = {
                Text(
                    stringResource(R.string.nombre),
                    style = MaterialTheme.typography.labelLarge
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.primary,
                unfocusedContainerColor = MaterialTheme.colorScheme.onPrimary,
                focusedTextColor = MaterialTheme.colorScheme.tertiary,
                unfocusedTextColor = MaterialTheme.colorScheme.onTertiary,
                focusedLabelColor = MaterialTheme.colorScheme.tertiary,
                unfocusedLabelColor = MaterialTheme.colorScheme.onTertiary
            )
        )

        Spacer(Modifier.height(8.dp))

        TextField(
            value = if(productoUiState.precio == 0) "" else productoUiState.precio.toString(),
            onValueChange = { viewModel.onPrecioChanged(it) },
            shape = AppShape.medium,
            singleLine = true,
            visualTransformation = CLPVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            label = {
                Text(
                    stringResource(R.string.precio),
                    style = MaterialTheme.typography.labelLarge
                )
            },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.primary,
                unfocusedContainerColor = MaterialTheme.colorScheme.onPrimary,
                focusedTextColor = MaterialTheme.colorScheme.tertiary,
                unfocusedTextColor = MaterialTheme.colorScheme.onTertiary,
                focusedLabelColor = MaterialTheme.colorScheme.tertiary,
                unfocusedLabelColor = MaterialTheme.colorScheme.onTertiary
            )
        )

        Spacer(Modifier.height(8.dp))

        Row {
            TextField(
                value = if (productoUiState.cantidad == 0) "" else productoUiState.cantidad.toString(),
                onValueChange = { viewModel.onCantidadChanged(it) },
                shape = AppShape.medium,
                singleLine = true,
                modifier = Modifier.weight(5f),
                label = {
                    Text(
                        stringResource(R.string.cantidad),
                        style = MaterialTheme.typography.labelLarge
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.primary,
                    unfocusedContainerColor = MaterialTheme.colorScheme.onPrimary,
                    focusedTextColor = MaterialTheme.colorScheme.tertiary,
                    unfocusedTextColor = MaterialTheme.colorScheme.onTertiary,
                    focusedLabelColor = MaterialTheme.colorScheme.tertiary,
                    unfocusedLabelColor = MaterialTheme.colorScheme.onTertiary
                )
            )

            Spacer(Modifier.width(8.dp))

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier.weight(3f)
            ) {
                TextField(
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    readOnly = true,
                    value = productoUiState.unidad,
                    onValueChange = { },
                    shape = AppShape.medium,
                    singleLine = true,
                    label = {
                        Text(
                            stringResource(R.string.unidad),
                            style = MaterialTheme.typography.labelLarge
                        )
                    },
                    // Icono de flecha
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.primary,
                        unfocusedContainerColor = MaterialTheme.colorScheme.onPrimary,
                        focusedTextColor = MaterialTheme.colorScheme.tertiary,
                        unfocusedTextColor = MaterialTheme.colorScheme.onTertiary,
                        focusedLabelColor = MaterialTheme.colorScheme.tertiary,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onTertiary
                    )
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    unidades.forEach { unidad ->
                        DropdownMenuItem(
                            text = { Text(unidad) },
                            onClick = {
                                viewModel.onUnidadChanged(unidad) // Actualiza el ViewModel
                                expanded = false                 // Cierra el menú
                            },
                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                        )
                    }
                }
            }
        }

        Spacer(Modifier.height(8.dp))

        Button(
            modifier = Modifier,
            onClick = { viewModel.onProductoAdded() },
            enabled = productoUiState.isProductoEnabled,
            shape = AppShape.medium,
            elevation = ButtonDefaults.buttonElevation(2.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                disabledContainerColor = MaterialTheme.colorScheme.onSecondary,
                contentColor = MaterialTheme.colorScheme.background
            )
        ) {
            Text(
                text = stringResource(R.string.guardar_producto),
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp)
            )
        }

    }

}