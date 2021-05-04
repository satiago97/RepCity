package com.example.repcity.api

data class User(

    val id: Int,
    val email: String?,
    val password: String?


)

data class Address(
    val id_address: Int,
    val tipo: String,
    val descricao: String,
    val lat: String,
    val lng: String,
    val id_user: Int
)