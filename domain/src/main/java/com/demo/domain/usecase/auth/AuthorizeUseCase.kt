package com.demo.domain.usecase.auth

import com.demo.domain.entity.repository.AuthRepository
import javax.inject.Inject

class AuthorizeUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    
    suspend operator fun invoke() {
        if (!authRepository.isAuthorized)
            authRepository.auth()
    } 
    
}