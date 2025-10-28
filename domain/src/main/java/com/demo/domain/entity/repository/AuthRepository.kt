package com.demo.domain.entity.repository

interface AuthRepository {
    val userId: String
    val isAuthorized: Boolean
    suspend fun auth()
}