package com.example.practicandomvvm.presentation.iu.operationProduct

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.practicandomvvm.domain.model.Product
import com.example.practicandomvvm.domain.usecase.product.GetProductByIdUserCase
import com.example.practicandomvvm.domain.usecase.product.SaveProductUseCase
import com.example.practicandomvvm.presentation.common.UiState
import com.example.practicandomvvm.presentation.common.makeCall
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OperationProductViewModel @Inject constructor(
    private val getProductByIdUserCase: GetProductByIdUserCase,
    private val saveProductUseCase: SaveProductUseCase
): ViewModel() {

    private val _itemProduct = MutableLiveData<Product?>(null)
    val itemProduct: LiveData<Product?> = _itemProduct

    private val _uiStateProduct = MutableLiveData<UiState<Product?>>()
    val uiStateProduct: LiveData<UiState<Product?>> = _uiStateProduct

    private val _uiStateSave = MutableLiveData<UiState<Int>>()
    val uiStateSave: LiveData<UiState<Int>> = _uiStateSave


    fun setItem(model: Product?){
        _itemProduct.value = model
    }

    fun getProductById(id:Int){
        viewModelScope.launch {
            _uiStateProduct.value = UiState.Loading()

            makeCall { getProductByIdUserCase(id) }.let {
                if (it is UiState.Success){
                    _itemProduct.value = it.data

                    _uiStateProduct.value = it
                }
            }
        }
    }

    fun save(model: Product){
        viewModelScope.launch {
            _uiStateSave.value = UiState.Loading()

            makeCall { saveProductUseCase(model) }.let {
                _uiStateSave.value = it
            }
        }
    }
}