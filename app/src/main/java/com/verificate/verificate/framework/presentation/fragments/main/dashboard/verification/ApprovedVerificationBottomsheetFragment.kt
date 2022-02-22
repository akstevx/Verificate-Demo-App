package com.verificate.verificate.framework.presentation.fragments.main.dashboard.verification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.verificate.verificate.databinding.FragmentApprovedVerificationBottomsheetBinding
import com.verificate.verificate.model.response.verification.Verification
import com.verificate.verificate.util.SingleLiveEvent
import com.verificate.verificate.util.argument
import com.verificate.verificate.util.withArguments

class ApprovedVerificationBottomsheetFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentApprovedVerificationBottomsheetBinding? = null
    private val binding get() = _binding!!
    private val verification:Verification by argument(VERIFICATION)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentApprovedVerificationBottomsheetBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnContinue.setOnClickListener { dialog?.dismiss() }
        binding.btnLocateInMap.setOnClickListener {
            onLocateOnMap.value = verification
            dialog?.dismiss()
        }
    }

    companion object {
        val VERIFICATION = "verification"
        val onLocateOnMap = SingleLiveEvent<Verification>()

        fun newInstance(verification: Verification): ApprovedVerificationBottomsheetFragment {
            return ApprovedVerificationBottomsheetFragment().withArguments(VERIFICATION to verification)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}