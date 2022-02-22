package com.verificate.verificate.framework.datasource.repository.abstraction

import com.verificate.verificate.model.request.*
import com.verificate.verificate.model.response.GeneralResponse
import com.verificate.verificate.model.response.login.LoginResponse
import com.verificate.verificate.model.response.states.GetStatesResponse
import com.verificate.verificate.model.response.states.lga.LGAResponse
import com.verificate.verificate.util.UseCaseResult

interface AuthRepository {
    suspend fun loginUser(request: LoginRequest): UseCaseResult<LoginResponse>
    suspend fun getStates():UseCaseResult<GetStatesResponse>
    suspend fun getStateLGAs(param:String): LGAResponse
    suspend fun initiateCreateUser(request: InitiateEnrollmentRequest): UseCaseResult<GeneralResponse>
    suspend fun createUser(request: CompleteEnrollmentRequest): UseCaseResult<GeneralResponse>
    suspend fun initiateResetPassword(request: InitiateResetPassword): UseCaseResult<GeneralResponse>
    suspend fun completeResetPassword(request: CompleteResetPassword): UseCaseResult<GeneralResponse>
}