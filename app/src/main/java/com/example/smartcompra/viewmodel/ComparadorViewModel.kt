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
    private val _productoUiState = MutableStateFlow(ProductoUiState())
    val productoUiState: StateFlow<ProductoUiState> = _productoUiState

    val productos = listOf(
        Producto("Shampoo", "Lider",650, 100000, "un", 10,3,100000, "Unidad", true),
        Producto("Acondicionador", "",70000, 1000, "un", 0,0,50000, "Litro", false),
        Producto("Jabón", "",300, 10000, "un",0,0, 80000, "Kilogramo", false),
        Producto("Pasta dental", "",12000, 1000, "un",0,0, 30000, "Metro", false)
    )

    private val _productList = MutableStateFlow<List<Producto>>(productos)

    //private val _productList = MutableStateFlow<List<Producto>>(emptyList())

    val productList: StateFlow<List<Producto>> = _productList
    fun onNombreChanged(nombre: String) {
        _productoUiState.update {
            _productoUiState.value.copy( nombre = nombre)
        }

        verifyInput()
    }

    fun onMarcaChanged(marca: String?) {
        _productoUiState.update {
            _productoUiState.value.copy( marca = marca)
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

    fun onDescuentoChanged(descuentoString: String) {
        try {
            val descuento = if(descuentoString.isEmpty()) 0 else descuentoString.toInt()
            _productoUiState.update {
                _productoUiState.value.copy(descuento = descuento)
            }

            verifyInput()
        } catch (e: NumberFormatException) {
            println("Error de formato: $descuentoString no es un número válido.")
        }

    }

    fun onPackChanged(packString: String) {
        try {
            val pack = if(packString.isEmpty()) 0 else packString.toInt()
            _productoUiState.update {
                _productoUiState.value.copy(pack = pack)
            }

            verifyInput()
        } catch (e: NumberFormatException) {
            println("Error de formato: $packString no es un número válido.")
        }

    }

    private fun verifyInput() {
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
            marca = _productoUiState.value.marca,
            cantidad = _productoUiState.value.cantidad,
            precio = _productoUiState.value.precio,
            unidad = _productoUiState.value.unidad,
            descuento = _productoUiState.value.descuento,
            pack = _productoUiState.value.pack,
            precioNormalizado = CalcularValorNormalizado(
                _productoUiState.value.cantidad,
                _productoUiState.value.precio,
                _productoUiState.value.descuento,
                _productoUiState.value.pack,
                _productoUiState.value.unidad,
            ),
            unidadNormalizada = CalcularUnidadNormalizada(_productoUiState.value.unidad)
        )
        _productList.update {currentList ->
            currentList + newProducto
        }

        _productoUiState.update {
            _productoUiState.value.copy( isProductoEnabled = false )
        }
        _productoUiState.update { ProductoUiState() }

        VerificarMejorPrecio()

        sortProductosByBestPrice()
    }

    private fun CalcularValorNormalizado (cantidad: Int, precio: Int, descuento: Int, pack: Int, unidad: String): Int{
        var precioNormalizado: Double
        if ( unidad == "g" || unidad == "mL" ){
            precioNormalizado = ((precio.toDouble() * ((100.0 - descuento)/100.0)) / ((cantidad.toDouble() * pack) / 1000.0))
        } else {
            precioNormalizado = ((precio.toDouble() * ((100.0 - descuento)/100.0)) / (cantidad.toDouble() * pack))
        }
        return precioNormalizado.toInt()
    }

    private fun VerificarMejorPrecio () {
        val productosActuales = _productList.value

        val menorPrecio = productosActuales.minOfOrNull { it.precioNormalizado }

        _productList.update { currentList ->
            currentList.map { producto ->
                val isBest = producto.precioNormalizado == menorPrecio
                producto.copy(bestPrice = isBest)
            }
        }
    }

    fun onProductoDeleted(producto: Producto) {
        _productList.update { currentList ->
            currentList - producto
        }
        VerificarMejorPrecio()
        sortProductosByBestPrice()
    }

    private fun sortProductosByBestPrice(){
        _productList.update { currentList ->
            currentList.sortedBy { it.precioNormalizado }
        }
    }

    private fun CalcularUnidadNormalizada(unidadIngresada: String): String{
        if (unidadIngresada == "un"){
            return "Unidad"
        }else if (unidadIngresada == "m"){
            return "Metro"
        }else if (unidadIngresada == "g" || unidadIngresada == "Kg"){
            return "Kilogramo"
        } else if (unidadIngresada == "mL" || unidadIngresada == "L") {
            return "Litro"
        } else {
            return unidadIngresada
        }
    }

}

fun isNombreValid(nombre: String): Boolean = nombre.length >= 3

fun isCantidadValid(cantidad: Int): Boolean = cantidad > 0

fun isPrecioValid(precio: Int): Boolean = precio > 0

fun isUnidadValid(unidad: String): Boolean = !unidad.isEmpty()


data class ProductoUiState(
    val nombre: String = "",
    val marca: String? = "",
    val cantidad: Int = 0,
    val precio: Int = 0,
    val unidad: String = "",
    val descuento: Int = 0,
    val pack: Int = 1,
    val precioNormalizado: Int = 0,
    val isProductoEnabled: Boolean = false,
    val bestPrice: Boolean = false,
    val unidadNormalizada: String = "",
)
