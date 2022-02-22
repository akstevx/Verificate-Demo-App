package com.verificate.verificate.framework.datasource.repository.implementation

import com.verificate.verificate.base.*
import com.verificate.verificate.framework.datasource.remote.ApiService
import com.verificate.verificate.framework.datasource.remote.StatesServiceApi
import com.verificate.verificate.framework.datasource.repository.abstraction.AuthRepository
import com.verificate.verificate.model.request.*
import com.verificate.verificate.model.response.GeneralResponse
import com.verificate.verificate.model.response.login.LoginResponse
import com.verificate.verificate.model.response.states.GetStatesResponse
import com.verificate.verificate.model.response.states.lga.LGAResponse
import com.verificate.verificate.util.UseCaseResult
import com.verificate.verificate.util.capitalize
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(private val apiService: ApiService, private val statesServiceApi: StatesServiceApi,
                                             private val localStorage: LocalStorage) : AuthRepository, BaseRepository() {
    override suspend fun loginUser(request: LoginRequest): UseCaseResult<LoginResponse> {
        return safeApiCall(request, apiService::loginUser, {it.responseCode == "00"}, this::saveLoginDetails)
    }
    private suspend fun saveLoginDetails(request: LoginRequest, response:LoginResponse){
        localStorage.setCustomerEmail(response.userEmail)
        localStorage.setCustomerPhoneNumber(response.userPhone)
        localStorage.setAuthorization(response.userToken)
        localStorage.setUserID(response.userId)
        localStorage.setBikeStatus(response.userBikeStatus)
        localStorage.setUserState(response.userStateOfResidence)
        localStorage.setUserState(response.userLGA)
        localStorage.setCustomerFullName("${response.userFirstName.capitalize()} ${response.userLastName.capitalize()}")
        localStorage.setCustomerFirstname(response.userFirstName.capitalize())
        localStorage.setCustomerLastname(response.userLastName.capitalize())
        localStorage.setOnlineStatus(true)
        localStorage.setPasscode(request.password)
    }

    override suspend fun getStates(): UseCaseResult<GetStatesResponse> {
        return safeGetApiCall( statesServiceApi::getAllStates) { it.state.isNotEmpty() }
    }

    override suspend fun getStateLGAs(param:String): LGAResponse {
        return statesServiceApi.getAllStateLgas(param)
    }

    override suspend fun initiateCreateUser(request: InitiateEnrollmentRequest): UseCaseResult<GeneralResponse> {
        return safeRequestApiCall(request,apiService::initiateCreateUser, {it.responseCode == "00"}, this::saveEnrolledDetails)
    }

    private suspend fun saveEnrolledDetails(request: InitiateEnrollmentRequest){
        localStorage.setCustomerEmail(request.userEmail)
        localStorage.setCustomerPhoneNumber(request.userPhone)
        localStorage.setCustomerFullName("${request.userFirstName.capitalize()} ${request.userLastName.capitalize()}")
    }

    override suspend fun createUser(request: CompleteEnrollmentRequest): UseCaseResult<GeneralResponse> {
        return safeApiCall(request,apiService::completeCreateUser, {it.responseCode == "00"})
    }

    override suspend fun initiateResetPassword(request: InitiateResetPassword): UseCaseResult<GeneralResponse> {
        return safeApiCall(request, apiService::initiateResetPassword, {it.responseCode == "00"})
    }

    override suspend fun completeResetPassword(request: CompleteResetPassword): UseCaseResult<GeneralResponse> {
        return safeApiCall(request, apiService::completeResetPassword, {it.responseCode == "00"})
    }

}