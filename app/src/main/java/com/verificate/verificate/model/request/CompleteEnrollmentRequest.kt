package com.verificate.verificate.model.request

data class CompleteEnrollmentRequest(
    val userEmail: String,
    val userOtp: String,
    val userPassword: String,
    val userPasswordConfirmation: String
)