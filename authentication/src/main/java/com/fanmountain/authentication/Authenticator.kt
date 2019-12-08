package com.fanmountain.authentication

import com.google.firebase.auth.FirebaseAuth
import com.tanikoszyk.domain.Result
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

interface Authenticator {
    suspend fun signIn(email: String, password: String): Result<String>
    suspend fun signUp(email: String, password: String): Result<String>
    suspend fun signOut()
    suspend fun isUserLoggedIn(): Boolean
    suspend fun getUserId(): String
}

internal class FirebaseAuthenticator(private val firebaseAuth: FirebaseAuth) : Authenticator {

    override suspend fun signIn(email: String, password: String): Result<String> {
        return suspendCoroutine { continuation ->
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    continuation.resume(
                        if (task.isSuccessful) {
                            Result.Success.WithBody(task.result?.user?.uid.orEmpty())
                        } else {
                            Result.Failure(task.exception ?: Throwable())
                        }
                    )
                }
        }
    }

    override suspend fun signUp(email: String, password: String): Result<String> {
        return suspendCoroutine { continuation ->
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    continuation.resume(
                        if (task.isSuccessful) {
                            Result.Success.WithBody(task.result?.user?.uid.orEmpty())
                        } else {
                            Result.Failure(task.exception ?: Throwable())
                        }
                    )
                }
        }
    }

    override suspend fun signOut() = firebaseAuth.signOut()

    override suspend fun isUserLoggedIn() = firebaseAuth.currentUser != null

    override suspend fun getUserId(): String = firebaseAuth.currentUser?.uid.orEmpty()
}