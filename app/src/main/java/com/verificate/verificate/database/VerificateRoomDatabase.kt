package com.verificate.verificate.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.verificate.verificate.database.dao.VerificationDao
import com.verificate.verificate.model.response.verification.Verification

@Database(entities = [Verification::class, ], version = 1, exportSchema = false)
abstract class VerificateRoomDatabase : RoomDatabase() {
    abstract fun verificationDao(): VerificationDao

}