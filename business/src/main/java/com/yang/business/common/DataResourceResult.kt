package com.yang.business.common

sealed class DataResourceResult<out T> {
    data object Uninitialized : DataResourceResult<Nothing>()

    data object Loading : DataResourceResult<Nothing>()

    data class Success<out T>(
        val data: T
    ) : DataResourceResult<T>()

    data class Failure(
        val exception: Throwable
    ) : DataResourceResult<Nothing>()

}