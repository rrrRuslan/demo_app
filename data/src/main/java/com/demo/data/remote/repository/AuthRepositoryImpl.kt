package com.demo.data.remote.repository

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import weatherusa.domain.preferences.DevicePreferences
import weatherusa.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val devicePreferences: DevicePreferences,
): AuthRepository {

    private val auth by lazy { Firebase.auth }

    override val userId: String
        get() = devicePreferences.deviceId
//        get() = auth.currentUser?.uid ?: throw IllegalStateException("User is not authenticated")

    override val isAuthorized: Boolean
        get() = true
//        get() = auth.currentUser?.uid != null

    override suspend fun auth() {
//        auth.signInAnonymously().await()
//        Timber.d("User authenticated with user id: ${auth.currentUser?.uid}")
    }

}