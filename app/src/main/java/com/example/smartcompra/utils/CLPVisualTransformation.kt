package com.example.smartcompra.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class CLPVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        // 1. Convertir el texto de entrada a un número (Int), ignorando no-dígitos
        val cleanText = text.text.filter { it.isDigit() }
        val numberValue = cleanText.toIntOrNull() ?: 0

        // 2. Aplicar el formato CLP
        val formattedValue: String

        if (numberValue == 0) {
            formattedValue = ""
        } else {
            // 2. Si no es cero, aplica el formato CLP (ej: "$ 1.234")
            formattedValue = numberValue.toChileanPesos()
        }

        // 3. Mapeo de Posición (OffsetMapping)
        // Esto es esencial para que el cursor del usuario se ubique correctamente.
        val offsetMapping = object : OffsetMapping {

            // Mapea la posición del cursor en el texto original (sin formato) a la posición
            // en el texto con formato ($ 1.234)
            override fun originalToTransformed(offset: Int): Int {
                // Calcula la diferencia de longitud
                val diff = formattedValue.length - cleanText.length
                // El nuevo offset es el original más la diferencia de longitud
                return (offset + diff).coerceIn(0, formattedValue.length)
            }

            // Mapea la posición del cursor en el texto con formato ($ 1.234) a la posición
            // en el texto original (1234)
            override fun transformedToOriginal(offset: Int): Int {
                // Calcula la diferencia de longitud
                val diff = formattedValue.length - cleanText.length
                // El nuevo offset es el transformado menos la diferencia de longitud
                return (offset - diff).coerceIn(0, cleanText.length)
            }
        }

        return TransformedText(
            // El resultado visual es el texto formateado
            text = AnnotatedString(formattedValue),
            // La lógica de posicionamiento del cursor
            offsetMapping = offsetMapping
        )
    }
}