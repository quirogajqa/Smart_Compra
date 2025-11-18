package com.example.smartcompra.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartcompra.data.local.ShoppingListDao
import com.example.smartcompra.data.models.ArticuloToSave
import com.example.smartcompra.data.models.ListaCompra
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val shoppingListDao: ShoppingListDao,
) : ViewModel() {

    private val _UiState = MutableStateFlow(UiState())
    val UiState: StateFlow<UiState> = _UiState
    private val _comprasList = MutableStateFlow<List<ListaCompra>>(emptyList())
    val comprasList: StateFlow<List<ListaCompra>> = _comprasList

    private val _productos = MutableStateFlow<List<ArticuloToSave>>(emptyList())
    val productos: StateFlow<List<ArticuloToSave>> = _productos

    private val _criterioOrdenamiento = MutableStateFlow(OrdenamientoCriterio.INGRESO_DSC)
    val criterioOrdenamiento: StateFlow<OrdenamientoCriterio> = _criterioOrdenamiento.asStateFlow()

    init {
        loadLists()
    }

    private fun loadLists() {
        _comprasList.value = emptyList()
        _UiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val cachedArticles = withContext(Dispatchers.IO) {
                shoppingListDao.getAllListas()
            }
            _comprasList.value = cachedArticles
            _UiState.update { it.copy(isLoading = false) }
        }
    }

    fun loadListaConProductos(id: Long) {
        _productos.value = emptyList()
        _UiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val cachedArticles = withContext(Dispatchers.IO) {
                shoppingListDao.getListaConProductos(id)
            }
            _productos.value = cachedArticles.productos
            _UiState.update { it.copy(isLoading = false) }
        }
    }

    fun onListaDeleted(listaCompra: ListaCompra){
        viewModelScope.launch {
            shoppingListDao.deleteLista(listaCompra)
        }
        _comprasList.update { currentList ->
            currentList - listaCompra
        }
    }

    fun setCriterioOrdenamiento(criterio: OrdenamientoCriterio) {
        _criterioOrdenamiento.value = criterio
        reordenarLista()
    }

    private fun reordenarLista() {
        val criterio: OrdenamientoCriterio = _criterioOrdenamiento.value
        return when (criterio) {

            OrdenamientoCriterio.INGRESO_ASC -> {
                _productos.update { currentList ->
                    currentList.sortedBy { it.fechaIngreso }
                }
            }

            OrdenamientoCriterio.INGRESO_DSC -> {
                _productos.update { currentList ->
                    currentList.sortedByDescending { it.fechaIngreso }
                }
            }

            OrdenamientoCriterio.NOMBRE_ASC -> {
                _productos.update { currentList ->
                    currentList.sortedBy { it.nombre }
                }
            }

            OrdenamientoCriterio.NOMBRE_DSC -> {
                _productos.update { currentList ->
                    currentList.sortedByDescending { it.nombre }
                }
            }
        }
    }
}

data class UiState(
    val isLoading: Boolean = false,
)