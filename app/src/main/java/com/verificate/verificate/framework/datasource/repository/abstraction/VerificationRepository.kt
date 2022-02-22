package com.verificate.verificate.framework.datasource.repository.abstraction

import com.verificate.verificate.model.request.GetAllVerificationsRequest
import com.verificate.verificate.model.request.VerificationActionRequest
import com.verificate.verificate.model.response.GeneralResponse
import com.verificate.verificate.model.response.verification.GetAllVerificationsResponse
import com.verificate.verificate.util.UseCaseResult

interface VerificationRepository {
    suspend fun updateVerificationStatus(request: VerificationActionRequest): UseCaseResult<GeneralResponse>
    suspend fun getAllVerifications(request: GetAllVerificationsRequest): UseCaseResult<GetAllVerificationsResponse>
    suspend fun getAllVerificationsWorker(request: GetAllVerificationsRequest): Boolean
}
