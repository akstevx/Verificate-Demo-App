package com.verificate.verificate.model.request

data class UpdateStatusRequest(
    val userId: String,
    val userStatus: String
)