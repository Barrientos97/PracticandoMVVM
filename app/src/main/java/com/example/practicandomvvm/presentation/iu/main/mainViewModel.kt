package com.example.practicandomvvm.presentation.iu.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicandomvvm.domain.model.Product
import com.example.practicandomvvm.domain.usecase.product.DeleteProductUseCase
import com.example.practicandomvvm.domain.usecase.product.GetListApiUseCase
import com.example.practicandomvvm.domain.usecase.product.GetListUseCase
import com.example.practicandomvvm.presentation.common.UiState
import com.example.practicandomvvm.presentation.common.makeCall
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class mainViewModel @Inject constructor(
    private val getListUseCase: GetListUseCase,
    private val deleteUseCase: DeleteProductUseCase,
    private val getListApiUseCase: GetListApiUseCase
): ViewModel() {

    private val _listProduct = MutableStateFlow<List<Product>>(emptyList())
    val listProduct: StateFlow<List<Product>> = _listProduct

    private val _uiStateList = MutableStateFlow<UiState<List<Product>>?>(null)
    val uiStateList: StateFlow<UiState<List<Product>>?> = _uiStateList


    private val _uiStateDelete = MutableStateFlow<UiState<Int>?>(null)
    val uiStateDelete: StateFlow<UiState<Int>?> = _uiStateDelete


    init {
        getList("")
    }

    fun resetUiStateList(){
        _uiStateList.value = null
    }

    fun resetUiStateDelete(){
        _uiStateDelete.value = null
    }

    private fun getList(dato: String){
        viewModelScope.launch {
            _uiStateList.value = UiState.Loading()
            makeCall {
                getListUseCase(dato).collect{
                    _listProduct.value = UiState.Success(it).data

                    _uiStateList.value = UiState.Success(it)
                }
            }
        }
    }

    fun delete (model: Product){
        viewModelScope.launch {
            _uiStateDelete.value = UiState.Loading()
            makeCall {
                deleteUseCase(model)
            }.let {
                _uiStateDelete.value = it
            }
        }
    }

    fun getListApi(dato: String){
        viewModelScope.launch {
            _uiStateList.value = UiState.Loading()
            makeCall {
                getListApiUseCase(dato)
            }.let {
                _uiStateList.value = it
            }
        }
    }

}