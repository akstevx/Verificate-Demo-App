package com.verificate.verificate.framework.datasource.repository.implementation

import com.verificate.verificate.base.BaseRepository
import com.verificate.verificate.base.LocalStorage
import com.verificate.verificate.framework.datasource.remote.ApiService
import com.verificate.verificate.framework.datasource.repository.abstraction.AgentRepository
import com.verificate.verificate.model.request.UpdateStatusRequest
import com.verificate.verificate.model.response.GeneralResponse
import com.verificate.verificate.util.UseCaseResult
import javax.inject.Inject

class AgentRepositoryImpl @Inject constructor(private val localStorage: LocalStorage,
                                              private val apiService: ApiService): AgentRepository, BaseRepository()  {

    override suspend fun updateAgentStatus(request: UpdateStatusRequest): UseCaseResult<GeneralResponse> {
        return safeApiCall(request, apiService::updateStatus) { it.responseCode == "00" }
    }

}