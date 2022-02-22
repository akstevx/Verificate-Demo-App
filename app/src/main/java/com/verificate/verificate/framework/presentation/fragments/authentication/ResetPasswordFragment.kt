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
import com.verificate.verificate.databinding.FragmentResetPasswordBinding
import com.verificate.verificate.framework.presentation.viewmodels.AuthViewModel
import com.verificate.verificate.util.Validator

class ResetPasswordFragment : BaseFragment() {
    private var _binding : FragmentResetPasswordBinding?  = null
    private val binding get() = _binding!!
    private val viewModel: AuthViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        showNavigationBar(true, "Reset Password")
        _binding =  FragmentResetPasswordBinding.inflate(inflater,container,false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateUI()
        setObservers()
    }

    private fun setObservers(){
        setUpObservers(viewModel)
        viewModel.initiateResetPasswordResponse.observeChange(viewLifecycleOwner){
            viewModel.email = binding.etEmail.text.toString()
            mFragmentNavigation.pushFragment(ResetPasswordOtpFragment())
        }
    }

    private fun updateUI() {
        binding.btnResetPassword.setOnClickListener {
            if (Validator.isValidEmail(binding.etEmail)) {
                viewModel.resetPasswordInit(binding.etEmail.text.toString())
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }
}