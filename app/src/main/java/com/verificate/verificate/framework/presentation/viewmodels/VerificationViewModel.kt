package com.verificate.verificate.framework.presentation.viewmodels

import androidx.lifecycle.asLiveData
import com.verificate.verificate.base.BaseViewModel
import com.verificate.verificate.framework.datasource.local.VerificationDatasource
import com.verificate.verificate.framework.datasource.repository.abstraction.VerificationRepository
import com.verificate.verificate.model.request.GetAllVerificationsRequest
import com.verificate.verificate.model.request.VerificationActionRequest
import com.verificate.verificate.model.response.GeneralResponse
import com.verificate.verificate.model.response.verification.GetAllVerificationsResponse
import com.verificate.verificate.model.response.verification.Verification
import com.verificate.verificate.util.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VerificationViewModel @Inject constructor(private val verificationRepository: VerificationRepository,
                                                private val verificationDatasource: VerificationDatasource): BaseViewModel() {

    val getAllVerifications = SingleLiveEvent<GetAllVerificationsResponse>()
    val verificationActionResponse = SingleLiveEvent<GeneralResponse>()

    var hasVerificationsLoaded = false
    var currentVerification: Verification? = null

    fun getAssignedVerifications(){
        val request = GetAllVerificationsRequest()
        return apiRequest(request, verificationRepository::getAllVerifications, getAllVerifications, { it.responseMessage ?: "" }, displayLoading = false)
    }

    fun makeVerificationAction(verificationId:String, verificationStatus:VerificationStatus){
        return when(verificationStatus){
            VerificationStatus.ACCEPT -> {
                apiRequest(VerificationActionRequest(verificationId = verificationId, verificationStatus = "PENDING"),
                    verificationRepository::updateVerificationStatus,
                    verificationActionResponse, { it.responseMessage ?: "" }, displayLoading = true, onSuccessOperations = {
                        this.getAssignedVerifications()
                    })
            }
            VerificationStatus.DECLINE -> {
                apiRequest(VerificationActionRequest(verificationId = verificationId, verificationStatus = "ACTIVE"),
                    verificationRepository::updateVerificationStatus,
                    verificationActionResponse, { it.responseMessage ?: "" }, displayLoading = true, onSuccessOperations = {
                        this.getAssignedVerifications()
                    })
            }
        }
    }

    fun displayVerifications() = verificationDatasource.getAllVerifications().asLiveData()

    enum class VerificationStatus{
        ACCEPT, DECLINE
    }
}