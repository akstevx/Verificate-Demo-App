package com.verificate.verificate.model.request

data class LoginRequest(
    val empty: String="",
    val password: String,
    val username: String
)