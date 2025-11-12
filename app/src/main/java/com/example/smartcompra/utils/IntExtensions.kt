package com.example.smartcompra.utils

import java.text.NumberFormat
import java.util.Locale

/**
 * Convierte un Double a una cadena con formato de Peso Chileno (CLP).
 */
fun Int.toChileanPesos(): String {
    // Definimos el Locale para Español (Chile)
    val format = NumberFormat.getCurrencyInstance(Locale("es", "CL"))

    // Aseguramos que no haya decimales, que es la convención habitual del CLP.
    format.maximumFractionDigits = 0

    // Formatea el número: ej. $1.234
    val formattedString = format.format(this)

    // Reemplazamos el '$' por '$ ' para agregar el espacio
    return formattedString.replace("$", "$ ")
}

/**
 * Convierte una cadena de texto con formato de Peso Chileno (CLP),
 * incluyendo el espacio explícito, a un Int.
 */
fun String.fromChileanPesosToInt(): Int {
    // 1. Manejar el espacio explícito agregado en la función toChileanPesos()
    val stringSinEspacio = this.replace("$ ", "$")

    // 2. Definir el Locale para Español (Chile)
    val format = NumberFormat.getCurrencyInstance(Locale("es", "CL"))

    // 3. Parsear la cadena a un número
    return try {
        // NumberFormat.parse() lee el formato de moneda y el separador de miles.
        // Devuelve un Number, que convertimos a Int.
        format.parse(stringSinEspacio)?.toInt() ?: 0
    } catch (e: Exception) {
        // En caso de que la cadena no tenga el formato esperado (ej. "hola")
        println("Error al parsear el formato de moneda CLP: ${e.message}")
        0
    }
}