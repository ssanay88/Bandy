package com.yang.business.enums

sealed interface ExceptionType {
    object AlertDialog : ExceptionType
    object SnackBar : ExceptionType
}