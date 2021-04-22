package com.example.repcity.api

import com.example.repcity.LoginResponse
import retrofit2.Call
import retrofit2.http.*

interface EndPoints {


@FormUrlEncoded
@POST("/api/login")
fun userLogin(
    @Field("email") email: String,
    @Field("password") password: String
):Call<LoginResponse>

}