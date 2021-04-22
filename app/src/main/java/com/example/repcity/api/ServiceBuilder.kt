package com.example.repcity.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {

    private val client = OkHttpClient.Builder().build()

val instance: EndPoints by lazy {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://manchus-arcs.000webhostapp.com/myslim/index.php/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()


    retrofit.create(EndPoints::class.java)

}


}