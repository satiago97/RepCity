package com.example.repcity

import com.example.repcity.api.User

data class LoginResponse(val error: Boolean,  val message: String, val user: User)