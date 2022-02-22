package com.verificate.verificate.model.request

data class InitiateEnrollmentRequest(
    val userAccountNo: String="",

    val userAddress: String,

    val userBVN: String="",

    val userBikeStatus: String,
    val userCountry: String="Nigeria",
    val userDate: String="",
    val userDateOfBirth: String,
    val userEmail: String,
    val userFirstName: String,
    val userGender: String,
    val userLGA: String,
    val userLastName: String,

    val userOrganisationId: String="00000001",
    val userPassword: String="",

    val userPhone: String,

    val userRating: String="",

    val userStateOfResidence: String,
    val userType: String= "Agent"
)