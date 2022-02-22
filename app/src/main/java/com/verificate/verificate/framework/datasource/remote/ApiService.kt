package com.verificate.verificate.framework.datasource.remote


import com.verificate.verificate.model.request.*
import com.verificate.verificate.model.response.GeneralResponse
import com.verificate.verificate.model.response.login.LoginResponse
import com.verificate.verificate.model.response.states.GetStatesResponse
import com.verificate.verificate.model.response.states.lga.LGAResponse
import com.verificate.verificate.model.response.verification.GetAllVerificationsResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.*

interface ApiService {

    @POST("initiateEnrollment")
    fun initiateCreateUser(@Body params: InitiateEnrollmentRequest): Deferred<GeneralResponse>

    @POST("completeEnrollment")
    fun completeCreateUser(@Body params: CompleteEnrollmentRequest): Deferred<GeneralResponse>

    @POST("logon")
    fun loginUser(@Body params: LoginRequest): Deferred<LoginResponse>

    @POST("initiateResetPassword")
    fun initiateResetPassword(@Body params: InitiateResetPassword): Deferred<GeneralResponse>

    @POST("completeResetPassword")
    fun completeResetPassword(@Body params: CompleteResetPassword): Deferred<GeneralResponse>

    @POST("avcVerificationRead")
    fun getAccountVerifications(@Body params: GetAllVerificationsRequest): Deferred<GetAllVerificationsResponse>

    @POST("avcUserUpdateStatus")
    fun updateStatus(@Body params: UpdateStatusRequest): Deferred<GeneralResponse>

    @POST("avcVerificationUpdateStatus")
    fun updateVerificationStatus(@Body params: VerificationActionRequest): Deferred<GeneralResponse>

/*    @POST("profile/confirm-otp")
    fun confirmOtp(@Body params: ConfirmOtpRequest): Deferred<ConfirmOtpResponse>*/
}

interface StatesServiceApi{
    @GET("states")
    fun getAllStates():Deferred<GetStatesResponse>

    @GET("state/{state}/kaduna/lgas")
    suspend fun getAllStateLgas(
        @Path("state") state: String
    ): LGAResponse
}