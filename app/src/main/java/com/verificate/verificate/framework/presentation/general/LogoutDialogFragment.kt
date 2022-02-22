package com.verificate.verificate.framework.presentation.general

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.verificate.verificate.base.LocalStorage
import com.verificate.verificate.base.setLogInStatus
import com.verificate.verificate.base.setSignedInStatus
import kotlinx.android.synthetic.main.fragment_logout_dialog.*
import com.verificate.verificate.databinding.FragmentLogoutDialogBinding


class LogoutDialogFragment : BottomSheetDialogFragment() {
    private var _binding: FragmentLogoutDialogBinding?= null
    private val binding get() = _binding!!
    private lateinit var localStorage:LocalStorage

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        localStorage = LocalStorage(context = requireContext() )
        _binding = FragmentLogoutDialogBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateUI()
    }


    private fun updateUI() {
        binding.btnCancel.setOnClickListener {
            dialog?.dismiss()
        }

        binding.btnLogout.setOnClickListener {
            dialog?.dismiss()
            localStorage.setLogInStatus(status = false)
            localStorage.setSignedInStatus(status = true)
            requireActivity().finish()
        }

    }

}