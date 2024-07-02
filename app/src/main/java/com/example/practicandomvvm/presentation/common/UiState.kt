package com.example.practicandomvvm.presentation.common

sealed class UiState<T>{
    class Loading<T>: UiState<T>()
    class Success<T>(val data: T): UiState<T>()
    class Error<T>(val message: String): UiState<T>()

}