package com.verificate.verificate.model.request

data class InitiateResetPassword(
    val countryCode: String,
    val userEmail: String
)