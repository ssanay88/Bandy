package com.yang.business.usecase.user

data class UserUseCases(
    val readUser: ReadUserUseCase,
    val createUser: CreateUserUseCase,
    val updateUser: UpdateUserUseCase,
    val deleteUser: DeleteUserUseCase,
    val searchUserByNickname: SearchUserByNicknameUseCase
)