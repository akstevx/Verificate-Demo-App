package com.verificate.verificate.framework.datasource.repository.abstraction

import com.verificate.verificate.model.request.UpdateStatusRequest
import com.verificate.verificate.model.response.GeneralResponse
import com.verificate.verificate.util.UseCaseResult

interface AgentRepository {
    suspend fun updateAgentStatus(request: UpdateStatusRequest): UseCaseResult<GeneralResponse>
}