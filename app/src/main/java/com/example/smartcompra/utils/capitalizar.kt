package com.example.smartcompra.utils

import java.util.Locale

fun String.toCapitalizar(): String {
    // 1. Verifica si la cadena está vacía. Si es así, devuelve la misma cadena.
    if (this.isEmpty()) return this

    // 2. Utiliza replaceFirstChar:
    return this.replaceFirstChar {
        // 3. Convierte el primer carácter a mayúscula usando el Locale por defecto
        if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
    }
}