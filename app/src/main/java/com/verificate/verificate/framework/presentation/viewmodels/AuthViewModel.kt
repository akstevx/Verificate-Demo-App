package com.verificate.verificate.framework.presentation.viewmodels

import android.content.Context
import com.verificate.verificate.base.BaseViewModel
import com.verificate.verificate.base.LocalStorage
import com.verificate.verificate.base.setOnlineStatus
import com.verificate.verificate.base.setUsername
import com.verificate.verificate.framework.datasource.repository.abstraction.AuthRepository
import com.verificate.verificate.model.request.*
import com.verificate.verificate.model.response.GeneralResponse
import com.verificate.verificate.model.response.login.LoginResponse
import com.verificate.verificate.model.response.states.GetStatesResponse
import com.verificate.verificate.model.response.states.lga.LGA
import com.verificate.verificate.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authRepository: AuthRepository, private val localStorage: LocalStorage,
                                        private val appContext: Context): BaseViewModel() {

    val initiateCreateUserResponse = SingleLiveEvent<GeneralResponse>()
    val resendPasswordResponse = SingleLiveEvent<GeneralResponse>()
    val createUserResponse = SingleLiveEvent<GeneralResponse>()
    val loginResponse = SingleLiveEvent<LoginResponse>()
    val statesResponse = SingleLiveEvent<GetStatesResponse>()
    val lgaResponse = SingleLiveEvent<List<String>>()
    val initiateResetPasswordResponse = SingleLiveEvent<GeneralResponse>()
    val resetPasswordResend = SingleLiveEvent<GeneralResponse>()
    val completeResetPasswordResponse = SingleLiveEvent<GeneralResponse>()

    var firstName = ""
    var lastName = ""
    var phoneNumber = ""
    var state = ""
    var lga = ""
    var email = ""
    var bikeStatus = ""
    var address = ""
    var dateOfBirth = ""
    var gender = ""
    var otp = ""
    var password = ""

    fun  login(password:String, email: String){
        val request = LoginRequest(username = email, password = password)
        return apiRequest(request, authRepository::loginUser, loginResponse, { it.responseMessage ?: "" }, onSuccessOperations = {
            localStorage.setOnlineStatus(true)
            localStorage.setUsername("${it.userFirstName} ${it.userLastName}")
        })
    }

    fun getAllStates(){
        getApiRequest(authRepository::getStates, statesResponse, { "Something went wrong" }, displayLoading = false)
    }

    fun getLGA(state: String){
        val list = LGA.list
        val stateLGA = list.find { statesLGAItem ->  statesLGAItem.state == state || statesLGAItem.alias == state}
        stateLGA?.lgas.let { lgaResponse.value = it }
    }

    fun resetPasswordInit(email:String){
        val request = InitiateResetPassword(userEmail = email, countryCode = "234")
        return apiRequest(request, authRepository::initiateResetPassword, initiateResetPasswordResponse, { it.responseMessage ?: "" })
    }

    fun resetPasswordResend(){
        val request = InitiateResetPassword(userEmail = email, countryCode = "234")
        return apiRequest(request, authRepository::initiateResetPassword, resetPasswordResend, { it.responseMessage ?: "" })
    }

    fun resetPasswordComplete(password:String){
        val request = CompleteResetPassword(userEmail = email, password = password, passwordConfirmation = password, userOtp = otp )
        return apiRequest(request, authRepository::completeResetPassword, completeResetPasswordResponse, { it.responseMessage ?: "" })
    }

    fun createUserInit(userFirstName:String, userLastName:String, localGovernment:String, state:String, phoneNumber:String, email:String,
                       address:String, dateOfBirth:String, gender:String, bikeStatus:String){
        val createUserRequest = InitiateEnrollmentRequest(userEmail = email, userBikeStatus = bikeStatus, userLGA = localGovernment, userStateOfResidence = state,
        userGender = gender, userPhone = phoneNumber, userDateOfBirth = dateOfBirth, userFirstName = userFirstName, userLastName = userLastName, userAddress = address)
        return apiRequest(createUserRequest, authRepository::initiateCreateUser, initiateCreateUserResponse, { it.responseMessage ?: "" })
    }

    fun resendOtp(){
        val createUserRequest = InitiateEnrollmentRequest(userEmail = email, userBikeStatus = bikeStatus, userLGA = lga, userStateOfResidence = state,
            userGender = gender, userPhone = phoneNumber, userDateOfBirth = dateOfBirth, userFirstName = firstName, userLastName = lastName, userAddress = address)
        return apiRequest(createUserRequest, authRepository::initiateCreateUser, resendPasswordResponse, { it.responseMessage ?: "" })
    }


    fun  createUser(){
        val request = CompleteEnrollmentRequest(userEmail = email, userOtp = otp, userPassword = password, userPasswordConfirmation = password)
        return apiRequest(request, authRepository::createUser, createUserResponse, { it.responseMessage ?: "" })
    }

}