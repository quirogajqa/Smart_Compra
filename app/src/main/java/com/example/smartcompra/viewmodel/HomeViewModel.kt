package com.example.smartcompra.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smartcompra.data.local.ShoppingListDao
import com.example.smartcompra.data.models.ArticuloComprado
import com.example.smartcompra.data.models.ArticuloToSave
import com.example.smartcompra.data.models.ListaCompra
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    init {
        loadLists()
    }

    private fun loadLists() {
        _UiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            val cachedArticles = withContext(Dispatchers.IO) { shoppingListDao.getAllListas() }
            _comprasList.value = cachedArticles
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
}

data class UiState(
    val isLoading: Boolean = false,
)