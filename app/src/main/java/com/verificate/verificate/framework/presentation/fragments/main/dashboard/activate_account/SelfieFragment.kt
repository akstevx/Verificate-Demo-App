package com.verificate.verificate.framework.presentation.fragments.main.dashboard.activate_account

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.verificate.verificate.R
import com.verificate.verificate.base.BaseFragment
import com.verificate.verificate.databinding.FragmentSelfieBinding
import com.verificate.verificate.framework.presentation.fragments.authentication.LoginFragment

class SelfieFragment : BaseFragment() {
    private var _binding : FragmentSelfieBinding?  = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding =  FragmentSelfieBinding.inflate(inflater,container,false)
        return _binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnProceedPassport.setOnClickListener {
            displaySuccess(successTitle = "Account Activated", successMessage = " Your account has been \nactivated successfully"){
                mFragmentNavigation.clearStack()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}