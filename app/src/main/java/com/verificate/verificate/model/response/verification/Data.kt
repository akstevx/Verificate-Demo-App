package com.verificate.verificate.model.response.verification

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

@Entity(tableName = "verifications")
data class Data(
    val verificationAddress: String,
    val verificationApplicantFirstName: String,
    val verificationApplicantLastName: String,
    val verificationApplicantMiddleName: String,
    val verificationChannel: String,
    val verificationCity: String,
    val verificationContactPersonName: String,
    val verificationCreatedAt: String,
    val verificationDate: String,
    val verificationEmployerEmail: String,
    val verificationEmployerName: String,
    val verificationEmploymentCurrentStatus: String,
    val verificationEmploymentEndDate: String,
    val verificationEmploymentStartDate: String,
    val verificationExpiryDate: String,
    val verificationGuarantorAddress: String,
    val verificationGuarantorCity: String,
    val verificationGuarantorEmail: String,
    val verificationGuarantorFirstName: String,
    val verificationGuarantorLastName: String,
    val verificationGuarantorMiddleName: String,
    val verificationGuarantorState: String,
    val verificationId: String,
    val verificationOrganisationId: String,
    val verificationState: String,
    val verificationStatus: String,
    val verificationType: String,
    val verificationUpdatedAt: String,
    @PrimaryKey val verificationUserId: String
) : Serializable

typealias Verification = Data