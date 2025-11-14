package com.example.smartcompra.viewmodel

import androidx.lifecycle.ViewModel
import com.example.smartcompra.data.models.Compra
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.annotation.meta.When
import kotlin.collections.plus

@HiltViewModel
class ComprasViewModel @Inject constructor(

) : ViewModel () {

    private val _comprasUiState = MutableStateFlow(ComprasUiState())
    val comprasUiState: StateFlow<ComprasUiState> = _comprasUiState

    val compras = listOf(
        Compra("Shampoo", 3,1650.0, 10, 1, 4455.0),
        Compra("Acondicionador", 3,2200.0, 10, 1, 5940.0),
        Compra("Jabón", 2,1650.0, 0, 1, 3300.0),
        Compra("Pasta dental", 1,3456.0, 5, 3,3283.0)
    )

    private val _comprasList = MutableStateFlow<List<Compra>>(compras)
    //private val _comprasList = MutableStateFlow<List<Compra>>(emptyList())
    val comprasList: StateFlow<List<Compra>> = _comprasList

    fun onNombreChanged(nombre: String) {
        _comprasUiState.update {
            _comprasUiState.value.copy( nombre = nombre)
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
        val newCompra = Compra(
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
            currentList + newCompra
        }

        _comprasUiState.update {
            _comprasUiState.value.copy( isButtonAddEnabled = false )
        }
        _comprasUiState.update { ComprasUiState() }
        reordenarLista()

        calcularTotal()
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

    fun onCompraDeleted(compra: Compra) {
        _comprasList.update { currentList ->
            currentList - compra
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

    private val _criterioOrdenamiento = MutableStateFlow(OrdenamientoCriterio.INGRESO_DSC)
    val criterioOrdenamiento: StateFlow<OrdenamientoCriterio> = _criterioOrdenamiento.asStateFlow()

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
    val showBottomSheet: Boolean = false
)

enum class OrdenamientoCriterio {
    INGRESO_ASC,
    INGRESO_DSC,
    NOMBRE_ASC,
    NOMBRE_DSC
}