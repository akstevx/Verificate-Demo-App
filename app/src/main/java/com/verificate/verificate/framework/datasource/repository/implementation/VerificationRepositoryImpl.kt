package com.verificate.verificate.framework.datasource.repository.implementation

import com.verificate.verificate.base.BaseRepository
import com.verificate.verificate.base.LocalStorage
import com.verificate.verificate.framework.datasource.local.VerificationDatasource
import com.verificate.verificate.framework.datasource.remote.ApiService
import com.verificate.verificate.framework.datasource.repository.abstraction.VerificationRepository
import com.verificate.verificate.model.request.GetAllVerificationsRequest
import com.verificate.verificate.model.request.VerificationActionRequest
import com.verificate.verificate.model.response.GeneralResponse
import com.verificate.verificate.model.response.verification.GetAllVerificationsResponse
import com.verificate.verificate.util.UseCaseResult
import timber.log.Timber
import javax.inject.Inject

class VerificationRepositoryImpl @Inject constructor( private val localStorage: LocalStorage, private val verificationDatasource: VerificationDatasource,
                                                     private val apiService: ApiService
): VerificationRepository, BaseRepository()  {
    override suspend fun updateVerificationStatus(request: VerificationActionRequest): UseCaseResult<GeneralResponse> {
        return safeApiCall(request, apiService::updateVerificationStatus, {it.responseCode == "00"})
    }

    override suspend fun getAllVerifications(request: GetAllVerificationsRequest): UseCaseResult<GetAllVerificationsResponse> {
        return safeApiCall(request, apiService::getAccountVerifications, {it.responseCode == "00"}, this::saveVerificationsFromResponse)
    }

    override suspend fun getAllVerificationsWorker(request: GetAllVerificationsRequest): Boolean{
        return safeWorkerApiCall(request, apiService::getAccountVerifications, {it.responseCode == "00"}, this::saveVerificationsFromResponse )
    }

    private suspend fun saveVerificationsFromResponse(response: GetAllVerificationsResponse){
        response.data?.let {
            verificationDatasource.insertVerifications(it)
        }
    }

}