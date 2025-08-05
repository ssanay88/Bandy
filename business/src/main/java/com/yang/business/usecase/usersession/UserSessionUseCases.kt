package com.yang.business.usecase.usersession

data class UserSessionUseCases(
    val getUserSession: GetUserSessionUseCase,
    val checkUserRegisteredUseCase: CheckUserRegisteredUseCase,
    val updateUserSession: UpdateUserSessionUseCase,
    val clearUserSession: ClearUserSessionUseCase
)