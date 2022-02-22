package com.verificate.verificate.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.verificate.verificate.base.BaseDao
import com.verificate.verificate.model.response.verification.Verification
import kotlinx.coroutines.flow.Flow
import timber.log.Timber

@Dao
interface VerificationDao : BaseDao<Verification> {
    @Query("select * from verifications")
    fun getAllVerifications() : Flow<List<Verification>>

    @Query("delete from verifications")
    fun deleteAllVerifications()

    @Transaction
    fun updateAllVerifications(verifications: List<Verification>){
        deleteAllVerifications()
        insertAll(verifications)
    }
}