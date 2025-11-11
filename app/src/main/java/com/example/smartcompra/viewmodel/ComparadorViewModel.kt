package com.example.smartcompra.viewmodel

import androidx.lifecycle.ViewModel
import com.example.smartcompra.data.models.Producto
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

@HiltViewModel
class ComparadorViewModel @Inject constructor(

) : ViewModel () {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    private val _producto = MutableStateFlow(Producto())
    val producto: StateFlow<Producto> = _producto

    private val _productoUiState = MutableStateFlow(ProductoUiState())
    val productoUiState: StateFlow<ProductoUiState> = _productoUiState

    fun onNombreChanged(nombre: String) {
        _producto.update {
            _producto.value.copy(nombre = nombre)
        }

        verifyInput()
    }

    fun onCantidadChanged(cantidadString: String) {
        try {
            val cantidad = if(cantidadString.isEmpty()) 0 else cantidadString.toInt()
            _producto.update {
                _producto.value.copy(cantidad = cantidad)
            }

            verifyInput()
        } catch (e: NumberFormatException) {
            println("Error de formato: $cantidadString no es un número válido.")
        }

    }

    fun onPrecioChanged(precioString: String) {
        try {
            val precio = if(precioString.isEmpty()) 0 else precioString.toInt()
            _producto.update {
                _producto.value.copy(precio = precio)
            }

            verifyInput()
        } catch (e: NumberFormatException) {
            println("Error de formato: $precioString no es un número válido.")
        }

    }

    fun onUnidadChanged(unidad: String) {
        _producto.update {
            _producto.value.copy(unidad = unidad)
        }

        verifyInput()
    }

    fun verifyInput() {
        val enabledAdd = isNombreValid(_producto.value.nombre)
                && isCantidadValid(_producto.value.cantidad)
                && isPrecioValid(_producto.value.precio)
                && isUnidadValid(_producto.value.unidad)
        _productoUiState.update {
            it.copy(isProductoEnabled = enabledAdd)
        }
    }

    fun onProductoAdded() {
        val newProducto = Producto(
            nombre = _producto.value.nombre,
            cantidad = _producto.value.cantidad,
            precio = _producto.value.precio,
            unidad = _producto.value.unidad,
            precioNormalizado = CalcularValorNormalizado(
                _producto.value.cantidad,
                _producto.value.precio,
                _producto.value.unidad
            )
        )
        _uiState.update {currentState ->
            currentState.copy(
                productos = currentState.productos + newProducto,
                isEmpty = false
            )
        }

        _productoUiState.update {
            _productoUiState.value.copy( isProductoEnabled = false )
        }
        _producto.update { Producto() }
    }

    fun CalcularValorNormalizado (cantidad: Int, precio: Int, unidad: String): Int{
        var precioNormalizado: Double
        if ( unidad == "g" || unidad == "mL" ){
            precioNormalizado = (precio.toDouble() / (cantidad.toDouble() / 1000))
        } else {
            precioNormalizado = (precio.toDouble() / cantidad.toDouble())
        }
        return precioNormalizado.toInt()
    }

}

fun isNombreValid(nombre: String): Boolean = nombre.length >= 3

fun isCantidadValid(cantidad: Int): Boolean = cantidad > 0

fun isPrecioValid(precio: Int): Boolean = precio > 0

fun isUnidadValid(unidad: String): Boolean = !unidad.isEmpty()


data class ProductoUiState(
    val isProductoEnabled: Boolean = false,
)
data class UiState(
    val isEmpty: Boolean = true,
    val productos: List<Producto> = emptyList()
)