package com.tanikoszyk.service.common

import android.content.Context
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import okhttp3.OkHttpClient
import pl.droidsonroids.retrofit2.JspoonConverterFactory
import retrofit2.Call
import retrofit2.Retrofit
import java.lang.Exception

fun createRetrofit(context: Context, baseUrl: String): Retrofit {
    val cookieJar = PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(context))

    val okHttpClient = OkHttpClient.Builder()
        .cookieJar(cookieJar)
        .build()

    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(JspoonConverterFactory.create())
        .build()
}

suspend inline fun <reified ResponseBody> Call<ResponseBody>.executeSafely(): Response<ResponseBody> {
    return GlobalScope.async {
        try {
            val response = execute()
            if (response.isSuccessful) {
                if (ResponseBody::class == Unit::class) {
                    Response.Success.Empty<ResponseBody>()
                } else {
                    Response.Success.WithBody(response.body()!!)
                }
            } else {
                Response.Failure<ResponseBody>(Throwable(response.errorBody()?.string().orEmpty()))
            }
        } catch (unknownException: Exception) {
            Response.Failure<ResponseBody>(unknownException)
        }
    }.await()
}

sealed class Response<ResponseBody> {
    sealed class Success<ResponseBody> : Response<ResponseBody>() {
        data class WithBody<ResponseBody>(val body: ResponseBody) : Success<ResponseBody>()
        class Empty<ResponseBody> : Success<ResponseBody>()
    }

    data class Failure<ResponseBody>(val throwable: Throwable) : Response<ResponseBody>()
}
