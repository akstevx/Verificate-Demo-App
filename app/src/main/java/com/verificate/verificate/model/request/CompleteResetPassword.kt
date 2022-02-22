package com.verificate.verificate.model.request

data class CompleteResetPassword(
    val password: String,
    val passwordConfirmation: String,
    val userEmail: String,
    val userOtp: String
)