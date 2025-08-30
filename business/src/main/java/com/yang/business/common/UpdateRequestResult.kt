package com.yang.business.common

sealed class UpdateRequestResult {
    object Initial : UpdateRequestResult()
    object Loading : UpdateRequestResult()
    object Success : UpdateRequestResult()
    data class Failure(val message: String) : UpdateRequestResult()
}