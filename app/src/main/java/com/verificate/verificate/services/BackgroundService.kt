package com.verificate.verificate.services

import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager

interface BackgroundService {
    fun startVerificationWorker()
}

class BackgroundServiceImpl():BackgroundService{
    override fun startVerificationWorker() {
        val networkRequiredContraint = androidx.work.Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val addVerications = OneTimeWorkRequestBuilder<GetVerificationsWorker>()
            .setConstraints(networkRequiredContraint)
            .addTag("ADD BANKS FROM API")
            .build()
        val workManager = WorkManager.getInstance()
        workManager.cancelAllWorkByTag("ADD BANKS FROM API")
        workManager.beginWith(addVerications).enqueue()
    }

}