package com.verificate.verificate.framework.presentation.fragments.authentication

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.activityViewModels
import com.verificate.verificate.R
import com.verificate.verificate.base.BaseFragment
import com.verificate.verificate.base.getCustomerEmail
import com.verificate.verificate.base.observeChange
import com.verificate.verificate.base.setLogInStatus
import com.verificate.verificate.databinding.FragmentLoginBinding
import com.verificate.verificate.framework.presentation.activities.MainActivity
import com.verificate.verificate.framework.presentation.viewmodels.AuthViewModel
import com.verificate.verificate.util.Validator
import com.verificate.verificate.util.delayFor

class LoginFragment : BaseFragment() {
    private var _binding: FragmentLoginBinding?= null
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
        showNavigationBar(toShow = false)
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateUI()
        setObservers()
    }

    private fun setObservers(){
        setUpObservers(viewModel)
        viewModel.loginResponse.observeChange(viewLifecycleOwner){
            localStorage.setLogInStatus(true)
            val myIntent = Intent(requireContext(), MainActivity::class.java)
            startActivity(myIntent)
        }
    }

    private fun updateUI(){
        binding.etLoginPasscode.setText("")
        localStorage.getCustomerEmail().let { binding.etEmail.setText(it) }
        binding.button12.setOnClickListener { mFragmentNavigation.popFragment() }
        binding.btnLogin.setOnClickListener {
            if (checkFields()){
                viewModel.login(
                    password = binding.etLoginPasscode.text.toString(),
                    email = binding.etEmail.text.toString()
                )
                delayFor(5000){ binding.etLoginPasscode.setText("") }
            }
        }
        binding.buttonLoginReg2.setOnClickListener { mFragmentNavigation.pushFragment(RegistrationFragment()) }

        binding.btnForgotPass.setOnClickListener {
            mFragmentNavigation.pushFragment(ResetPasswordFragment())
        }
    }

    private fun checkFields():Boolean {
        binding.etEmail.doAfterTextChanged {
            Validator.isValidEmail(binding.etEmail)
        }

        return Validator.isValidEmail(binding.etEmail) && !binding.etLoginPasscode.text.isNullOrEmpty()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}