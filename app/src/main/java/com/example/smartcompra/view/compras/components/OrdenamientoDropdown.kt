package com.example.smartcompra.view.compras.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.smartcompra.viewmodel.OrdenamientoCriterio

@Composable
fun OrdenamientoDropdown(
    criterioActual: OrdenamientoCriterio,
    onCriterioSeleccionado: (OrdenamientoCriterio) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box() {
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "Opciones de Ordenamiento",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            @Composable
            fun createMenuItem(criterio: OrdenamientoCriterio, texto: String) {
                val isSelected = criterioActual == criterio
                val itemColor = if (isSelected) {
                    MaterialTheme.colorScheme.tertiary
                } else {
                    MaterialTheme.colorScheme.onSurface
                }
                DropdownMenuItem(
                    text = {
                        Text(
                            text = texto,
                            color = itemColor
                        )
                    },
                    onClick = {
                        onCriterioSeleccionado(criterio)
                        expanded = false
                    }
                )
            }

            createMenuItem(OrdenamientoCriterio.NOMBRE_ASC, "NOMBRE ↓")
            createMenuItem(OrdenamientoCriterio.NOMBRE_DSC, "NOMBRE ↑")
            createMenuItem(OrdenamientoCriterio.INGRESO_ASC, "INGRESO ↓")
            createMenuItem(OrdenamientoCriterio.INGRESO_DSC, "INGRESO ↑")

        }
    }
}