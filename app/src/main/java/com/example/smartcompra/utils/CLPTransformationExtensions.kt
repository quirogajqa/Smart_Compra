package com.example.smartcompra.utils

import java.text.NumberFormat
import java.util.Locale
import kotlin.math.roundToInt

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
 * Convierte un Double a una cadena con formato de Peso Chileno (CLP),
 * redondeando al entero más cercano y añadiendo el espacio explícito.
 */
fun Double.toChileanPesos(): String {

    // 1. Redondea el Double al entero más cercano antes de formatear.
    val valorEntero = this.roundToInt()

    // 2. Definimos el Locale para Español (Chile)
    val format = NumberFormat.getCurrencyInstance(Locale("es", "CL"))

    // 3. Aseguramos que no haya decimales, ya que trabajamos con pesos enteros.
    format.maximumFractionDigits = 0
    format.minimumFractionDigits = 0 // Asegura que no se muestren decimales si el valor es .0

    // 4. Formatea el número: ej. $1.234
    val formattedString = format.format(valorEntero)

    // 5. Reemplazamos el '$' por '$ ' para agregar el espacio (como en tu función original)
    return formattedString.replace("$", "$ ")
}

// Ejemplo de uso:
// 1234.75.toChileanPesos() // Resultado: $ 1.235 (Redondeado)
// 999.30.toChileanPesos()  // Resultado: $ 999 (Redondeado)

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