package com.verificate.verificate.framework.datasource.local

import com.verificate.verificate.database.dao.VerificationDao
import com.verificate.verificate.model.response.verification.Verification
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import timber.log.Timber

interface VerificationDatasource {
    fun getAllVerifications() : Flow<List<Verification>>
    suspend fun insertVerifications(verificationsList: List<Verification>)
    suspend fun deleteVerifications()
}
class VerificationDatasourceImpl(private val verificationDao: VerificationDao): VerificationDatasource {
    override fun getAllVerifications(): Flow<List<Verification>> {
        return verificationDao.getAllVerifications()
    }

    override suspend fun insertVerifications(verificationsList: List<Verification>) {
        Timber.e("SAVING VERIFICATIONS FROM DATASOURCE")
        withContext(IO){
            verificationDao.updateAllVerifications(verificationsList)
        }
    }

    override suspend fun deleteVerifications() {
        withContext(IO){
            verificationDao.deleteAllVerifications()
        }
    }

}