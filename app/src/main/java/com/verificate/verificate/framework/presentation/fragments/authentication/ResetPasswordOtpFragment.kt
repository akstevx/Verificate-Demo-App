package com.verificate.verificate.framework.presentation.fragments.authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.verificate.verificate.R
import com.verificate.verificate.base.BaseFragment
import com.verificate.verificate.base.observeChange
import com.verificate.verificate.databinding.FragmentResetPasswordOtpBinding
import com.verificate.verificate.framework.presentation.viewmodels.AuthViewModel
import com.verificate.verificate.util.hideKeyboard

class ResetPasswordOtpFragment : BaseFragment() {
    private var _binding : FragmentResetPasswordOtpBinding?  = null
    private val binding get() = _binding!!
    private val viewModel: AuthViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding =  FragmentResetPasswordOtpBinding.inflate(inflater,container,false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObservers()
        binding.btnResetPassword.setOnClickListener {
            otpCheck()
        }

        binding.btnResendOtp.setOnClickListener {
            viewModel.resetPasswordResend()
        }
    }

    private fun setObservers() {
        setUpObservers(viewModel)
        viewModel.resetPasswordResend.observeChange(viewLifecycleOwner){
            showSuccess("New one time password has been sent to your mail")
        }
    }

    private fun otpCheck() {
        val otp = binding.otpView.text.toString()
        if (otp.length != 6) {
            showToast("Enter One Time Password Sent To Email")
        } else {
            saveToViewModel()
            hideKeyboard()
            binding.otpView.setText("")
            mFragmentNavigation.pushFragment(ResetPasswordFragment2())
        }
    }

    override fun saveToViewModel() {
        super.saveToViewModel()
        viewModel.otp = binding.otpView.text.toString()
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }
}