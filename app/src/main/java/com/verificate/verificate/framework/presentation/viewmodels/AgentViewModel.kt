package com.verificate.verificate.framework.presentation.viewmodels

import com.verificate.verificate.base.BaseViewModel
import com.verificate.verificate.base.LocalStorage
import com.verificate.verificate.base.getUserID
import com.verificate.verificate.framework.datasource.repository.abstraction.AgentRepository
import com.verificate.verificate.model.request.UpdateStatusRequest
import com.verificate.verificate.model.response.GeneralResponse
import com.verificate.verificate.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AgentViewModel @Inject constructor(private val agentRepository: AgentRepository,
                                         private val localStorage: LocalStorage): BaseViewModel() {
    private val agentStatusResponse = SingleLiveEvent<GeneralResponse>()

    fun updateAgentsStatus(status:Status) {
        when (status) {
            Status.DEACTIVATE -> {
                return apiRequest(
                    request = UpdateStatusRequest(userId = localStorage.getUserID(), userStatus = "PENDING"),
                    agentRepository::updateAgentStatus,
                    agentStatusResponse,
                    { it.responseMessage ?: "" }, )
            }
            Status.ACTIVATE -> {
                return apiRequest(
                    request = UpdateStatusRequest(userId = localStorage.getUserID(), userStatus = "ACTIVE"),
                    agentRepository::updateAgentStatus,
                    agentStatusResponse,
                    { it.responseMessage ?: "" })
            }
        }
    }

    enum class Status{ DEACTIVATE, ACTIVATE }

}