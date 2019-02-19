package com.github.justtwago.service.firebase

import com.github.justtwago.service.common.Response
import com.google.firebase.auth.FirebaseAuth
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

interface FirebaseAuthenticator {
    suspend fun signIn(email: String, password: String): Response<String>
    suspend fun signUp(email: String, password: String): Response<String>
    suspend fun signOut()
    suspend fun isUserLoggedIn(): Boolean
    suspend fun getUserId(): String
}

class FirebaseAuthenticatorImpl(private val firebaseAuth: FirebaseAuth) : FirebaseAuthenticator {

    override suspend fun signIn(email: String, password: String): Response<String> {
        return suspendCoroutine { continuation ->
            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    continuation.resume(
                        if (task.isSuccessful) {
                            Response.Success.WithBody(task.result?.user?.uid.orEmpty())
                        } else {
                            Response.Failure(task.exception ?: Throwable())
                        }
                    )
                }
        }
    }

    override suspend fun signUp(email: String, password: String): Response<String> {
        return suspendCoroutine { continuation ->
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    continuation.resume(
                        if (task.isSuccessful) {
                            Response.Success.WithBody(task.result?.user?.uid.orEmpty())
                        } else {
                            Response.Failure(task.exception ?: Throwable())
                        }
                    )
                }
        }
    }

    override suspend fun signOut() = firebaseAuth.signOut()

    override suspend fun isUserLoggedIn() = firebaseAuth.currentUser != null

    override suspend fun getUserId(): String = firebaseAuth.currentUser?.uid.orEmpty()
}