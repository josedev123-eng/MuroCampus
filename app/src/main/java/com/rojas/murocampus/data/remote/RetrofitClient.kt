package com.rojas.murocampus.data.remote

import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

object RetrofitClient {

    private val json = Json { ignoreUnknownKeys = true }

    // Agrega el encabezado Accept: application/json a todas las peticiones
    private val interceptorAccept = Interceptor { cadena ->
        val peticion = cadena.request().newBuilder()
            .header("Accept", "application/json")
            .build()
        cadena.proceed(peticion)
    }

    // Registro del trafico en Logcat (solo para desarrollo)
    private val cliente = OkHttpClient.Builder()
        .addInterceptor(interceptorAccept)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    val api: PostApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .client(cliente)
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(PostApi::class.java)
    }
}
