package com.simon.orisapp.http

import com.google.gson.GsonBuilder
import com.simon.orisapp.model.Link
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {
    private val client = OkHttpClient.Builder().build()
    private val gsonBuilder = GsonBuilder()
        .registerTypeAdapter(Link::class.java, LinkTypeAdapter())

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://oris.orientacnisporty.cz/")
        .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
        .client(client)
        .build()

    fun<T> buildService(service: Class<T>): T{
        return retrofit.create(service)
    }
}