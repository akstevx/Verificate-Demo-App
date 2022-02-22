package com.verificate.verificate.framework.presentation.fragments.main.dashboard.verification

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.verificate.verificate.R
import com.verificate.verificate.databinding.FragmentVerificationBottomsheetBinding
import com.verificate.verificate.model.response.verification.Verification
import com.verificate.verificate.util.*
import java.io.Serializable

class VerificationBottomsheetFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentVerificationBottomsheetBinding? = null
    private val binding get() = _binding!!
    private val verification:Verification by argument(VERIFICATION)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentVerificationBottomsheetBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateViews()
        setViews()
        binding.btnAccept.setOnClickListener {
            onActionSelected.value = Pair(ACTION.ACCEPT, verification)
            dialog?.dismiss()
        }

        binding.btnDecline.setOnClickListener {
            onActionSelected.value = Pair(ACTION.DECLINE, verification)
            dialog?.dismiss()
        }
    }

    private fun updateViews(){
        if (verification.verificationStatus != "ACTIVE") {
            binding.btnLocateOnMap.setOnClickListener{
                onLocateOnMap.value = verification
                dialog?.dismiss()
            }
            setApprovedState(true)
        } else setApprovedState(false)
    }

    private fun setApprovedState(isSet:Boolean){
        if(isSet){
            binding.btnLocateOnMap.show()
            binding.btnAccept.hide()
            binding.btnDecline.hide()
        } else {
            binding.btnLocateOnMap.hide()
            binding.btnAccept.show()
            binding.btnDecline.show()
        }

    }

    private fun setViews() {
        binding.txtName.text = verification.verificationContactPersonName
        binding.txtAddress.text = verification.verificationAddress
        binding.txtRequestor.text = "${verification.verificationApplicantFirstName} ${verification.verificationApplicantLastName}"
        binding.txtDueDate.text = getPatternFromDate(verification.verificationExpiryDate)
        binding.txtVerificationType.text = verification.verificationType
        binding.txtVerificationStatus.text = verification.verificationStatus.capitalizeWords()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        val VERIFICATION = "verification"
        val onActionSelected = SingleLiveEvent<Pair<ACTION, Verification>>()
        val onLocateOnMap = SingleLiveEvent<Verification>()
        fun newInstance(verification: Verification): VerificationBottomsheetFragment {
            return VerificationBottomsheetFragment().withArguments(VERIFICATION to verification)
        }
    }

    enum class ACTION{  ACCEPT, DECLINE}
}