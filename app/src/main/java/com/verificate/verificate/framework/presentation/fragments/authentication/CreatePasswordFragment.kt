package com.verificate.verificate.framework.presentation.fragments.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import com.verificate.verificate.R
import com.verificate.verificate.base.BaseFragment
import com.verificate.verificate.base.observeChange
import com.verificate.verificate.databinding.FragmentCreatePasswordBinding
import com.verificate.verificate.framework.presentation.viewmodels.AuthViewModel
import com.verificate.verificate.util.Validator

class CreatePasswordFragment : BaseFragment() {
    private var _binding: FragmentCreatePasswordBinding?= null
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
        showNavigationBar(true, "Confirm Password")
        _binding = FragmentCreatePasswordBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateViews()
    }

    private fun updateViews(){
        checkFields()
        binding.btnResetPassword.setOnClickListener {
            if (checkFields()){
                saveToViewModel()
                mFragmentNavigation.pushFragment(EnterOtpFragment())
            } else showError("Passwords did not match, enter password and confirm")
        }
    }


    private fun checkFields():Boolean{
        binding.etNewPassword.doAfterTextChanged {
            Validator.isValidNullPassword(binding.etNewPassword)
        }
        binding.etConfirmNewPassword.doAfterTextChanged {
            Validator.isValidNullPassword(binding.etConfirmNewPassword)
        }

        return binding.etNewPassword.text.toString() == binding.etConfirmNewPassword.text.toString() &&
                !binding.etNewPassword.text.isNullOrEmpty() && !binding.etConfirmNewPassword.text.isNullOrEmpty()
    }

    override fun saveToViewModel() {
        super.saveToViewModel()
        viewModel.password = binding.etNewPassword.text.toString()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}