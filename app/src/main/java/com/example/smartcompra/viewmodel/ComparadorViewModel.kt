package com.example.smartcompra.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartcompra.data.local.ArticuloCompradoDao
import com.example.smartcompra.data.local.ComparedArticleDao
import com.example.smartcompra.data.models.ArticuloComparado
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class ComparadorViewModel @Inject constructor(
    private val comparedArticleDao: ComparedArticleDao,
) : ViewModel () {
    private val _productoUiState = MutableStateFlow(ProductoUiState())
    val productoUiState: StateFlow<ProductoUiState> = _productoUiState



    private val _productList = MutableStateFlow<List<ArticuloComparado>>(emptyList())

    val productList: StateFlow<List<ArticuloComparado>> = _productList

    init {
        loadArticles()
    }

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
            val precio = if(precioString.isEmpty()) 0.0 else precioString.toDouble()
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
                && isDescuentoValid(_productoUiState.value.descuento)
                && isPackValid(_productoUiState.value.pack)
        _productoUiState.update {
            it.copy(isProductoEnabled = enabledAdd)
        }
        verifyClear()
    }

    private fun verifyClear() {
        val producto = _productoUiState.value
        val isEnabledClear =  producto.nombre.isNotEmpty()
                || !producto.marca.isNullOrEmpty()
                || producto.precio != 0.0
                || producto.cantidad != 0
                || producto.unidad.isNotEmpty()
                || producto.descuento != 0
                || producto.pack != 0

        _productoUiState.update {
            it.copy( isEnabledClear = isEnabledClear )
        }
    }

    fun onProductoAdded() {
        val newArticuloComparado = ArticuloComparado(
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
            currentList + newArticuloComparado
        }

        _productoUiState.update {
            _productoUiState.value.copy( isProductoEnabled = false )
        }
        val nombreAnterior = _productoUiState.value.nombre
        val unidadAnterior = _productoUiState.value.unidad
        _productoUiState.update { ProductoUiState(nombre = nombreAnterior, unidad = unidadAnterior) }

        viewModelScope.launch {
            comparedArticleDao.insertArticle(newArticuloComparado)
        }

        VerificarMejorPrecio()

        sortProductosByBestPrice()
    }

    private fun loadArticles(){
        viewModelScope.launch {
            val cachedArticles = comparedArticleDao.getAllComparedArticles()
            _productList.value = cachedArticles

            VerificarMejorPrecio()

            sortProductosByBestPrice()
        }
    }

    private fun CalcularValorNormalizado (cantidad: Int, precio: Double, descuento: Int, pack: Int, unidad: String): Double{
        var precioNormalizado: Double
        if ( unidad == "g" || unidad == "mL" ){
            precioNormalizado = ((precio * ((100.0 - descuento)/100.0)) / ((cantidad * pack) / 1000.0))
        } else {
            precioNormalizado = ((precio * ((100.0 - descuento)/100.0)) / (cantidad * pack))
        }
        return precioNormalizado
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

    fun onProductoDeleted(articuloComparado: ArticuloComparado) {
        viewModelScope.launch {
            comparedArticleDao.deleteArticleById(articuloComparado.id)
        }
        _productList.update { currentList ->
            currentList - articuloComparado
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

    fun onShowDialog(showDialog: Boolean){
        _productoUiState.update {
            _productoUiState.value.copy( showDialog = showDialog)
        }
    }

    fun clearShowDialog(){
        _productoUiState.update { ProductoUiState( showDialog = true ) }
    }

}

private fun isNombreValid(nombre: String): Boolean = nombre.length >= 3

private fun isCantidadValid(cantidad: Int): Boolean = cantidad > 0

private fun isPrecioValid(precio: Double): Boolean = precio > 0.0

private fun isDescuentoValid(descuento: Int): Boolean = descuento >= 0 && descuento <= 100
private fun isPackValid(pack: Int): Boolean = pack > 0

fun isUnidadValid(unidad: String): Boolean = !unidad.isEmpty()


data class ProductoUiState(
    val nombre: String = "",
    val marca: String? = "",
    val cantidad: Int = 0,
    val precio: Double = 0.0,
    val unidad: String = "",
    val descuento: Int = 0,
    val pack: Int = 1,
    val precioNormalizado: Double = 0.0,
    val isProductoEnabled: Boolean = false,
    val isEnabledClear: Boolean = false,
    val bestPrice: Boolean = false,
    val unidadNormalizada: String = "",
    val showDialog: Boolean = false
)
