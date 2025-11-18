package com.example.smartcompra.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartcompra.data.local.ArticuloCompradoDao
import com.example.smartcompra.data.models.ArticuloComprado
import com.example.smartcompra.utils.toCapitalizar
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.collections.plus

@HiltViewModel
class ComprasViewModel @Inject constructor(
    private val articuloCompradoDao: ArticuloCompradoDao,
) : ViewModel () {

    private val _comprasUiState = MutableStateFlow(ComprasUiState())
    val comprasUiState: StateFlow<ComprasUiState> = _comprasUiState


    private val _comprasList = MutableStateFlow<List<ArticuloComprado>>(emptyList())
    val comprasList: StateFlow<List<ArticuloComprado>> = _comprasList

    private val _criterioOrdenamiento = MutableStateFlow(OrdenamientoCriterio.INGRESO_DSC)
    val criterioOrdenamiento: StateFlow<OrdenamientoCriterio> = _criterioOrdenamiento.asStateFlow()

    init {
        loadArticles()
    }

    fun onNombreChanged(nombre: String) {
        _comprasUiState.update {
            _comprasUiState.value.copy( nombre = nombre.trim().toCapitalizar())
        }

        verifyInput()
    }

    fun onPrecioChanged(precioString: String) {
        try {
            val precio = if(precioString.isEmpty()) 0.0 else precioString.toDouble()
            _comprasUiState.update {
                _comprasUiState.value.copy(precio = precio)
            }

            verifyInput()
        } catch (e: NumberFormatException) {
            println("Error de formato: $precioString no es un número válido.")
        }

    }

    fun onCantidadChanged(cantidadString: String) {
        try {
            val cantidad = if(cantidadString.isEmpty()) 0 else cantidadString.toInt()
            _comprasUiState.update {
                _comprasUiState.value.copy(cantidad = cantidad)
            }

            verifyInput()
        } catch (e: NumberFormatException) {
            println("Error de formato: $cantidadString no es un número válido.")
        }

    }

    fun onDescuentoChanged(descuentoString: String) {
        try {
            val descuento = if(descuentoString.isEmpty()) 0 else descuentoString.toInt()
            _comprasUiState.update {
                _comprasUiState.value.copy(descuento = descuento)
            }

            verifyInput()
        } catch (e: NumberFormatException) {
            println("Error de formato: $descuentoString no es un número válido.")
        }

    }

    fun onPackChanged(packString: String) {
        try {
            val pack = if(packString.isEmpty()) 0 else packString.toInt()
            _comprasUiState.update {
                _comprasUiState.value.copy(pack = pack)
            }

            verifyInput()
        } catch (e: NumberFormatException) {
            println("Error de formato: $packString no es un número válido.")
        }

    }

    fun onCompraAdded() {
        val newArticuloComprado = ArticuloComprado(
            nombre = _comprasUiState.value.nombre,
            cantidad = _comprasUiState.value.cantidad,
            precio = _comprasUiState.value.precio,
            descuento = _comprasUiState.value.descuento,
            pack = _comprasUiState.value.pack,
            precioFinal = calcularPrecioFinal(
                _comprasUiState.value.cantidad,
                _comprasUiState.value.precio,
                _comprasUiState.value.descuento
            )
        )
        _comprasList.update {currentList ->
            currentList + newArticuloComprado
        }

        _comprasUiState.update {
            _comprasUiState.value.copy( isButtonAddEnabled = false )
        }
        _comprasUiState.update { ComprasUiState() }

        viewModelScope.launch {
            articuloCompradoDao.insertArticle(newArticuloComprado)
        }

        reordenarLista()

        calcularTotal()
    }

    private fun loadArticles(){
        _comprasUiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val cachedArticles = articuloCompradoDao.getAllPurchasedArticles()
            _comprasList.value = cachedArticles

            calcularTotal()
            _comprasUiState.update { it.copy(isLoading = false) }
        }
    }

    private fun verifyInput() {
        val enabledAdd = isNombreValid(_comprasUiState.value.nombre)
                && isPrecioValid(_comprasUiState.value.precio)
                && isDescuentoValid(_comprasUiState.value.descuento)
                && isPackValid(_comprasUiState.value.pack)
        _comprasUiState.update {
            it.copy(isButtonAddEnabled = enabledAdd)
        }
        verifyClear()
    }

    private fun verifyClear() {
        val producto = _comprasUiState.value
        val isEnabledClear =  producto.nombre.isNotEmpty()
                || producto.precio != 0.0
                || producto.cantidad != 0
                || producto.descuento != 0
                || producto.pack != 0

        _comprasUiState.update {
            it.copy( isEnabledClear = isEnabledClear )
        }
    }

    private fun calcularPrecioFinal(cantidad: Int, precio: Double, descuento: Int): Double{
        val precioFinal = (cantidad * precio) * (100 - descuento)/100
        return precioFinal
    }

    private fun calcularTotal(){
        val newTotal = _comprasList.value.sumOf { it.precioFinal }
        _comprasUiState.update {
            it.copy( total = newTotal )
        }
    }

    fun onCompraDeleted(articuloComprado: ArticuloComprado) {
        viewModelScope.launch {
            articuloCompradoDao.deleteArticleById(articuloComprado.id)
        }
        _comprasList.update { currentList ->
            currentList - articuloComprado
        }
        calcularTotal()
    }

    fun onShowDialog(showDialog: Boolean){
        _comprasUiState.update {
            _comprasUiState.value.copy( showBottomSheet = showDialog)
        }
    }

    fun clearShowDialog(){
        _comprasUiState.update { ComprasUiState( showBottomSheet = true ) }
    }

    fun setCriterioOrdenamiento(criterio: OrdenamientoCriterio) {
        _criterioOrdenamiento.value = criterio
        reordenarLista()
    }
    private fun reordenarLista() {
        val criterio: OrdenamientoCriterio = _criterioOrdenamiento.value
        return when (criterio){

            OrdenamientoCriterio.INGRESO_ASC -> {
                _comprasList.update { currentList ->
                    currentList.sortedBy { it.fechaIngreso }
                }
            }

            OrdenamientoCriterio.INGRESO_DSC -> {
                _comprasList.update { currentList ->
                    currentList.sortedByDescending { it.fechaIngreso }
                }
            }

            OrdenamientoCriterio.NOMBRE_ASC -> {
                _comprasList.update { currentList ->
                    currentList.sortedBy { it.nombre }
                }
            }

            OrdenamientoCriterio.NOMBRE_DSC -> {
                _comprasList.update { currentList ->
                    currentList.sortedByDescending { it.nombre }
                }
            }
        }
    }
}

private fun isNombreValid(nombre: String): Boolean = nombre.length >= 3
private fun isPrecioValid(precio: Double): Boolean = precio > 0.0
private fun isDescuentoValid(descuento: Int): Boolean = descuento >= 0 && descuento <= 100
private fun isPackValid(pack: Int): Boolean = pack > 0

data class ComprasUiState(
    val nombre: String = "",
    val cantidad: Int = 0,
    val precio: Double = 0.0,
    val descuento: Int = 0,
    val pack: Int = 1,
    val precioFinal: Double = 0.0,
    val total: Double = 0.0,
    val isButtonAddEnabled: Boolean = false,
    val isEnabledClear: Boolean = false,
    val showBottomSheet: Boolean = false,
    val isLoading: Boolean = false
)

enum class OrdenamientoCriterio {
    INGRESO_ASC,
    INGRESO_DSC,
    NOMBRE_ASC,
    NOMBRE_DSC
}