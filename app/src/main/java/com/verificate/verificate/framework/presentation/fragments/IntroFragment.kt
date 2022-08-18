package com.verificate.verificate.framework.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.verificate.verificate.R
import com.verificate.verificate.base.BaseFragment
import com.verificate.verificate.databinding.FragmentIntroBinding
import com.verificate.verificate.framework.presentation.fragments.authentication.LoginFragment
import com.verificate.verificate.framework.presentation.fragments.authentication.RegistrationFragment

class IntroFragment : BaseFragment() {
    private var binding: FragmentIntroBinding?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        showNavigationBar(toShow = false)
        binding = FragmentIntroBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnLogin?.setOnClickListener { mFragmentNavigation.pushFragment(LoginFragment()) }
        binding?.btnSignUp?.setOnClickListener { mFragmentNavigation.pushFragment(RegistrationFragment()) }

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}