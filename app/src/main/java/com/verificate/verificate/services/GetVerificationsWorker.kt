package com.verificate.verificate.services

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.verificate.verificate.framework.datasource.repository.abstraction.VerificationRepository
import com.verificate.verificate.model.request.GetAllVerificationsRequest
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

@HiltWorker
class GetVerificationsWorker @AssistedInject constructor(
    private val verificationRepository: VerificationRepository,
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters): Worker(appContext, workerParams), CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext = Dispatchers.IO + job

    companion object{
        val TAG = GetVerificationsWorker::class.java.simpleName
    }

    private fun refreshVerificationList():Boolean{
        return  runBlocking {
            val needsRetry = try {
                Timber.i("Before API Call")
                verificationRepository.getAllVerificationsWorker(GetAllVerificationsRequest())
            } catch (ex:Exception){
                Timber.e(ex)
                true
            }
            Timber.i(needsRetry.toString())
            needsRetry
        }
    }

    override fun doWork(): Result {
        return if(refreshVerificationList()){
            Timber.i("Worker Needs Retry")
            Result.retry()
        }else{
            Timber.i("Worker Successful")
            Result.success()
        }
    }

}