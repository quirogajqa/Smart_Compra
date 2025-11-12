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

    private val _productoUiState = MutableStateFlow(ProductoUiState())
    val productoUiState: StateFlow<ProductoUiState> = _productoUiState

    fun onNombreChanged(nombre: String) {
        _productoUiState.update {
            _productoUiState.value.copy( nombre = nombre)
        }

        verifyInput()
    }

    fun onCantidadChanged(cantidadString: String) {
        try {
            val cantidad = if(cantidadString.isEmpty()) 0 else cantidadString.toInt()
            _productoUiState.update {
                _productoUiState.value.copy(cantidad = cantidad)
            }

            verifyInput()
        } catch (e: NumberFormatException) {
            println("Error de formato: $cantidadString no es un número válido.")
        }

    }

    fun onPrecioChanged(precioString: String) {
        try {
            val precio = if(precioString.isEmpty()) 0 else precioString.toInt()
            _productoUiState.update {
                _productoUiState.value.copy(precio = precio)
            }

            verifyInput()
        } catch (e: NumberFormatException) {
            println("Error de formato: $precioString no es un número válido.")
        }

    }

    fun onUnidadChanged(unidad: String) {
        _productoUiState.update {
            _productoUiState.value.copy(unidad = unidad)
        }

        verifyInput()
    }

    fun verifyInput() {
        val enabledAdd = isNombreValid(_productoUiState.value.nombre)
                && isCantidadValid(_productoUiState.value.cantidad)
                && isPrecioValid(_productoUiState.value.precio)
                && isUnidadValid(_productoUiState.value.unidad)
        _productoUiState.update {
            it.copy(isProductoEnabled = enabledAdd)
        }
    }

    fun onProductoAdded() {
        val newProducto = Producto(
            nombre = _productoUiState.value.nombre,
            cantidad = _productoUiState.value.cantidad,
            precio = _productoUiState.value.precio,
            unidad = _productoUiState.value.unidad,
            precioNormalizado = CalcularValorNormalizado(
                _productoUiState.value.cantidad,
                _productoUiState.value.precio,
                _productoUiState.value.unidad
            ),
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
        _productoUiState.update { ProductoUiState() }

        VerificarMejorPrecio()
    }

    fun CalcularValorNormalizado (cantidad: Int, precio: Int, unidad: String): Int{
        var precioNormalizado: Double
        if ( unidad == "g" || unidad == "mL" ){
            precioNormalizado = (precio.toDouble() / (cantidad.toDouble() / 1000.0))
        } else {
            precioNormalizado = (precio.toDouble() / cantidad.toDouble())
        }
        return precioNormalizado.toInt()
    }

    fun VerificarMejorPrecio () {
        val productosActuales = _uiState.value.productos

        val menorPrecio = productosActuales.minOfOrNull { it.precioNormalizado }

        val productosActualizados = productosActuales.map { producto ->

            val isBest = producto.precioNormalizado == menorPrecio

            producto.copy(bestPrice = isBest)
        }

        _uiState.update { currentState ->
            currentState.copy(
                productos = productosActualizados
            )
        }
    }

}

fun isNombreValid(nombre: String): Boolean = nombre.length >= 3

fun isCantidadValid(cantidad: Int): Boolean = cantidad > 0

fun isPrecioValid(precio: Int): Boolean = precio > 0

fun isUnidadValid(unidad: String): Boolean = !unidad.isEmpty()


data class ProductoUiState(
    val nombre: String = "",
    val cantidad: Int = 0,
    val precio: Int = 0,
    val unidad: String = "",
    val precioNormalizado: Int = 0,
    val isProductoEnabled: Boolean = false,
    val bestPrice: Boolean = false
)
data class UiState(
    val isEmpty: Boolean = true,
    val productos: List<Producto> = emptyList()
)