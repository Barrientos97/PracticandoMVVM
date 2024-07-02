package com.example.practicandomvvm.presentation.iu.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicandomvvm.domain.model.Product
import com.example.practicandomvvm.domain.usecase.product.GetListUseCase
import com.example.practicandomvvm.presentation.common.UiState
import com.example.practicandomvvm.presentation.common.makeCall
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class mainViewModel @Inject constructor(
    private val getListUseCase: GetListUseCase
): ViewModel() {

    private val _listProduct = MutableStateFlow<List<Product>>(emptyList())
    val listProduct: StateFlow<List<Product>> = _listProduct

    private val _uiStateList = MutableStateFlow<UiState<List<Product>>?>(null)
    val uiStateList: StateFlow<UiState<List<Product>>?> = _uiStateList

    fun resetUiStateList(){
        _uiStateList.value = null
    }

    fun getList(dato: String){
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
}