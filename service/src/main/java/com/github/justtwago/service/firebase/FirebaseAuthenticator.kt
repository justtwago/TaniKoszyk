package com.github.justtwago.service.firebase

import com.github.justtwago.service.common.Result
import com.google.firebase.auth.FirebaseAuth
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


interface FirebaseAuthenticator {
    suspend fun signIn(login: String, password: String): Result<String>
    suspend fun signUp(login: String, password: String): Result<String>
    suspend fun signOut()
    suspend fun isUserLoggedIn(): Boolean
}

class FirebaseAuthenticatorImpl(private val firebaseAuth: FirebaseAuth) : FirebaseAuthenticator {

    override suspend fun signIn(login: String, password: String): Result<String> {
        return suspendCoroutine { continuation ->
            firebaseAuth.signInWithEmailAndPassword(login, password)
                .addOnCompleteListener { task ->
                    continuation.resume(
                        if (task.isSuccessful) {
                            Result.Success(task.result?.user?.uid.orEmpty())
                        } else {
                            Result.Failure(task.exception?.message.orEmpty())
                        }
                    )
                }
        }
    }

    override suspend fun signUp(login: String, password: String): Result<String> {
        return suspendCoroutine { continuation ->
            firebaseAuth.createUserWithEmailAndPassword(login, password)
                .addOnCompleteListener { task ->
                    continuation.resume(
                        if (task.isSuccessful) {
                            Result.Success(task.result?.user?.uid.orEmpty())
                        } else {
                            Result.Failure(task.exception?.message.orEmpty())
                        }
                    )
                }
        }
    }

    override suspend fun signOut() = firebaseAuth.signOut()

    override suspend fun isUserLoggedIn() = firebaseAuth.currentUser != null

}