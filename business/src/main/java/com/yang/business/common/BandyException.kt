package com.yang.business.common

import com.yang.business.enums.ExceptionType

sealed class BandyException(
    override val message: String?,
    val exceptionType: ExceptionType,
) : Throwable(message) {

    data class AlertException(
        override val message: String,
        val alertDialog: ExceptionType.AlertDialog
    ) : BandyException(message,ExceptionType.AlertDialog)

    data class SnackBarException(
        override val message: String,
        val snackBar: ExceptionType.SnackBar
    ) : BandyException(message, ExceptionType.SnackBar)
}