package com.verificate.verificate.framework.presentation.fragments.authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import com.verificate.verificate.R
import com.verificate.verificate.base.BaseFragment
import com.verificate.verificate.base.observeChange
import com.verificate.verificate.databinding.FragmentResetPassword2Binding
import com.verificate.verificate.framework.presentation.viewmodels.AuthViewModel
import com.verificate.verificate.util.Validator

class ResetPasswordFragment2 : BaseFragment() {
    private var _binding : FragmentResetPassword2Binding?  = null
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
        _binding =  FragmentResetPassword2Binding.inflate(inflater,container,false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateViews()
        setObserver()
    }

    private fun setObserver(){
        setUpObservers(viewModel)
        viewModel.completeResetPasswordResponse.observeChange(viewLifecycleOwner){
            showSuccessWithAction(successMessage = "Password has successfully been reset"){
                mFragmentNavigation.clearStack()
                mFragmentNavigation.pushFragment(LoginFragment())
            }
        }
    }

    private fun updateViews(){
        checkFields()
        binding.btnResetPassword.setOnClickListener {
            if (checkFields()){
                viewModel.resetPasswordComplete(password = binding.etNewPassword.text.toString())
            } else showError("Password did not match, enter password and confirm")
        }
    }

    private fun checkFields():Boolean{
        binding.etNewPassword.doAfterTextChanged {
            Validator.isValidNullPassword(binding.etNewPassword)
        }
        binding.etConfrimPassword.doAfterTextChanged {
            Validator.isValidNullPassword(binding.etConfrimPassword)
        }

        return binding.etNewPassword.text.toString() == binding.etConfrimPassword.text.toString()
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}