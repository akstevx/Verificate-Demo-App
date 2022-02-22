package com.verificate.verificate.model.response.login

data class LoginResponse(
    val responseCode: String,
    val responseMessage: String,
    val userAddress: String,
    val userBVN: String,
    val userBikeStatus: String,
    val userCountry: String,
    val userCreatedAt: String,
    val userDate: String,
    val userDateOfBirth: String,
    val userEmail: String,
    val userFirstName: String,
    val userGender: String,
    val userId: String,
    val userLGA: String,
    val userLastName: String,
    val userOrganisationId: String,
    val userOtp: String,
    val userPhone: String,
    val userRating: String,
    val userStateOfResidence: String,
    val userStatus: String,
    val userToken: String,
    val userType: String,
    val userUpdatedAt: String
)