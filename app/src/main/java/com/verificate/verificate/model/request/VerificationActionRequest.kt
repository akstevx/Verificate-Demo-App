package com.verificate.verificate.model.request

data class VerificationActionRequest(
    val verificationId: String,
    val verificationStatus: String
)