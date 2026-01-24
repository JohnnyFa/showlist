package com.fagundes.myshowlist.feat.login.domain

interface AuthRepository {
    suspend fun signInWithGoogle(idToken: String): Result<Unit>
}
