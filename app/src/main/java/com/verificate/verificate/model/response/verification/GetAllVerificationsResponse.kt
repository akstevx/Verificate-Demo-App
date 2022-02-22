package com.verificate.verificate.model.response.verification

data class GetAllVerificationsResponse(
    val `data`: List<Verification>,
    val responseCode: String,
    val responseMessage: String
)