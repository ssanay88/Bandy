package com.yang.business.model

data class ManageBandMenu(
    val title: String,
    val dialogTitle: String,
    val dialogDescription: String,
    val dialogConfirmButtonText: String,
    val dialogDismissButtonText: String,
    val onConfirmClick: () -> Unit,
    val onDismissClick: () -> Unit
)