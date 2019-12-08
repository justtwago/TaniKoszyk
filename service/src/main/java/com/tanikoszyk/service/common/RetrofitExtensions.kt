package com.tanikoszyk.service.common

import android.content.Context
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.tanikoszyk.domain.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import pl.droidsonroids.retrofit2.JspoonConverterFactory
import retrofit2.Call
import retrofit2.Retrofit

internal fun createRetrofit(context: Context, baseUrl: String): Retrofit {
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

suspend inline fun <reified ResponseBody, ResultBody> Call<ResponseBody>.execute(
    crossinline mapToResult: (ResponseBody) -> ResultBody
): Result<ResultBody> {
    return withContext(Dispatchers.Default) {
        try {
            val response = execute()
            if (response.isSuccessful) {
                if (ResponseBody::class == Unit::class) {
                    Result.Success.Empty<ResultBody>()
                } else {
                    Result.Success.WithBody(mapToResult(response.body()!!))
                }
            } else {
                Result.Failure<ResultBody>(Throwable(response.errorBody()?.string().orEmpty()))
            }
        } catch (unknownException: Exception) {
            Result.Failure<ResultBody>(unknownException)
        }
    }
}